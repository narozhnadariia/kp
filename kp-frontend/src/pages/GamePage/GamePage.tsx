import { useEffect, useState } from "react";
import { Link, useLocation,useNavigate } from "react-router-dom";

import Board from "../../components/Board/Board";
import PlayerPanel from "../../components/PlayerPanel/PlayerPanel";
import NextTile from "../../components/NextTile/NextTile";
import MoveControls from "../../components/MoveControls/MoveControls";
import ScoreGuide from "../../components/ScoreGuide/ScoreGuide";

import type { MoveDirection, TileType } from "../../types/game";

import { createGame, makeMove } from "../../api/gameApi";

//крок назад (те що вище потрібно буде закоментувати)
// import { createGame, makeMove, undoMove } from "../../api/gameApi";
//

//бомба
// import { createGame, makeMove, activateBomb} from "../../api/gameApi";
//

//поміняти плити
// import { createGame, makeMove, swapTiles } from "../../api/gameApi";
//

import "./GamePage.css";

const initialBoard: TileType[][] = [
    ["CHERRY", "BANANA", "PLUM", "BELL", "BAR"],
    ["PEAR", "SEVEN", "SEVEN", "BANANA", "PLUM"],
    ["BAR", "BANANA", "BELL", "BANANA", "PEAR"],
    ["CHERRY", "BAR", "CHERRY", "PLUM", "PLUM"],
    ["PLUM", "BELL", "PLUM", "PEAR", "SEVEN"],
];

//збільшити мапу
// const initialBoard: TileType[][] = [
//     ["CHERRY", "BANANA", "PLUM", "BELL", "BAR", "PEAR"],
//     ["PEAR", "SEVEN", "SEVEN", "BANANA", "PLUM", "CHERRY"],
//     ["BAR", "BANANA", "BELL", "BANANA", "PEAR", "SEVEN"],
//     ["CHERRY", "BAR", "CHERRY", "PLUM", "PLUM", "BANANA"],
//     ["PLUM", "BELL", "PLUM", "PEAR", "SEVEN", "BAR"],
//     ["BANANA", "PEAR", "BAR", "CHERRY", "BELL", "PLUM"],
// ];
//

export default function GamePage() {
    const location = useLocation();
    const navigate = useNavigate();

    const gameRequest = location.state as {
        gameMode: "PLAYER_VS_PLAYER" | "PLAYER_VS_BOT";
        player1Name: string;
        player2Name: string | null;
        botDifficulty: "EASY" | "HARD" | null;
    } | null;

    const opponentName =
        gameRequest?.gameMode === "PLAYER_VS_BOT"
            ? "Bot"
            : gameRequest?.player2Name ?? "Player 2";

    const [gameId, setGameId] = useState<number | null>(null);

    const [board, setBoard] = useState<TileType[][]>(initialBoard);
    const [nextTile, setNextTile] = useState<TileType>("CHERRY");

    const [player1Score, setPlayer1Score] = useState(0);
    const [player2Score, setPlayer2Score] = useState(0);

    const [player1Lamas, setPlayer1Lamas] = useState(5);
    const [player2Lamas, setPlayer2Lamas] = useState(5);

    const [currentPlayer, setCurrentPlayer] = useState<1 | 2>(1);
    const [winner, setWinner] = useState<string | null>(null);


    // поміняти плити місцями
    // const [selectedTile, setSelectedTile] = useState<{
    //     row: number;
    //     col: number;
    // } | null>(null);
//

    // темна тема
    // const [darkTheme, setDarkTheme] = useState(false);
    //

    // таймер
    // const [timeLeft, setTimeLeft] = useState(30);
    //

    // таймер + запис часу гри в score
    // const [gameTime, setGameTime] = useState(0);
    //

    // підказка
    // const [hint, setHint] = useState<string | null>(null);
    //

    useEffect(() => {
        if (gameRequest === null) {
            navigate("/");
            return;
        }

        async function startGame() {

            const request = gameRequest;

            const newGame = await createGame(request!);
            console.log(newGame.id);
            setGameId(newGame.id);
            setBoard(newGame.board);
            setNextTile(newGame.nextTile);

            setPlayer1Score(newGame.player1.score);
            setPlayer2Score(newGame.player2.score);

            setPlayer1Lamas(newGame.player1.lamas);
            setPlayer2Lamas(newGame.player2.lamas);

            setCurrentPlayer(newGame.currentPlayerName === newGame.player1.name ? 1 : 2);
            setWinner(newGame.winner);
        }

        startGame();
    }, [gameRequest, navigate]);



    //таймер
    // useEffect(() => {
    //     if (winner || !gameId) return;
    //
    //     const timer = setInterval(() => {
    //         setTimeLeft((prevTime) => {
    //             if (prevTime <= 1) {
    //                 return 30;
    //             }
    //
    //             return prevTime - 1;
    //         });
    //     }, 1000);
    //
    //     return () => clearInterval(timer);
    // }, [winner, gameId, currentPlayer]);
    //

    // таймер + запис часу гри в score
    // useEffect(() => {
    //     if (!gameId || winner) return;
    //
    //     const timer = setInterval(() => {
    //         setGameTime((prevTime) => prevTime + 1);
    //     }, 1000);
    //
    //     return () => clearInterval(timer);
    // }, [gameId, winner]);
    //

    useEffect(() => {
        if (!winner || !gameId) return;


        navigate("/feedback", {
            state: {
                gameId,
                winner,
                player1Name: gameRequest?.player1Name ?? "Player 1",
                player2Name: gameRequest?.player2Name ?? "Bot",
                player1Score,
                player2Score,

                // таймер + запис часу гри в score
                // gameTime,
                //

            },
        });
    }, [winner, gameId, navigate, gameRequest, player1Score, player2Score]);

    //анімація виграшу , те що вище буде треба закоментувати
    // useEffect(() => {
    //     if (!winner || !gameId) return;
    //
    //     const redirectTimer = setTimeout(() => {
    //         navigate("/feedback", {
    //             state: {
    //                 gameId,
    //                 winner,
    //                 player1Name: gameRequest?.player1Name ?? "Player 1",
    //                 player2Name: gameRequest?.player2Name ?? "Bot",
    //                 player1Score,
    //                 player2Score,
    //             },
    //         });
    //     }, 3000);
    //
    //     return () => clearTimeout(redirectTimer);
    // }, [winner, gameId, navigate, gameRequest, player1Score, player2Score]);
    //

    // таймер + запис часу гри в score
    // }, [winner, gameId, navigate, gameRequest, player1Score, player2Score, gameTime]);
    //

    const handleMove = async (direction: MoveDirection, index: number) => {
        if (!gameId) return;

        const updatedGame = await makeMove(gameId, direction, index);

        setBoard(updatedGame.board);
        setNextTile(updatedGame.nextTile);

        setPlayer1Score(updatedGame.player1.score);
        setPlayer2Score(updatedGame.player2.score);

        setPlayer1Lamas(updatedGame.player1.lamas);
        setPlayer2Lamas(updatedGame.player2.lamas);

        setCurrentPlayer(updatedGame.currentPlayerName === updatedGame.player1.name ? 1 : 2);
        setWinner(updatedGame.winner);
        // таймер
        // setTimeLeft(30);
        //
    };

    // поміняти плити місцями
    // const handleTileClick = async (row: number, col: number) => {
    //     if (!gameId) return;
    //
    //     if (selectedTile === null) {
    //         setSelectedTile({ row, col });
    //         return;
    //     }
    //
    //     const updatedGame = await swapTiles(
    //         gameId,
    //         selectedTile.row,
    //         selectedTile.col,
    //         row,
    //         col
    //     );
    //
    //     setSelectedTile(null);
    //
    //     setBoard(updatedGame.board);
    //     setNextTile(updatedGame.nextTile);
    //
    //     setPlayer1Score(updatedGame.player1.score);
    //     setPlayer2Score(updatedGame.player2.score);
    //
    //     setPlayer1Lamas(updatedGame.player1.lamas);
    //     setPlayer2Lamas(updatedGame.player2.lamas);
    //
    //     setCurrentPlayer(updatedGame.currentPlayerName === updatedGame.player1.name ? 1 : 2);
    //     setWinner(updatedGame.winner);
    // };
//

    //крок назад
    // const handleUndo = async () => {
    //     if (!gameId) return;
    //
    //     const updatedGame = await undoMove(gameId);
    //
    //     setBoard(updatedGame.board);
    //     setNextTile(updatedGame.nextTile);
    //
    //     setPlayer1Score(updatedGame.player1.score);
    //     setPlayer2Score(updatedGame.player2.score);
    //
    //     setPlayer1Lamas(updatedGame.player1.lamas);
    //     setPlayer2Lamas(updatedGame.player2.lamas);
    //
    //     setCurrentPlayer(updatedGame.currentPlayerName === updatedGame.player1.name ? 1 : 2);
    //     setWinner(updatedGame.winner);
    // };
    //


    // підказка
    // const handleHint = () => {
    //     const directions: MoveDirection[] = ["LEFT", "RIGHT", "TOP"];
    //     const randomDirection = directions[Math.floor(Math.random() * directions.length)];
    //     const randomIndex = Math.floor(Math.random() * 5);
    //
    //     const place = randomDirection === "TOP"
    //         ? `column ${randomIndex + 1}`
    //         : `row ${randomIndex + 1}`;
    //
    //     setHint(`Try: ${randomDirection} on ${place}`);
    // };
    //

    // бомба
    // const handleBomb = async () => {
    //     if (!gameId) return;
    //
    //     const updatedGame = await activateBomb(gameId);
    //
    //     setBoard(updatedGame.board);
    //     setNextTile(updatedGame.nextTile);
    //
    //     setPlayer1Score(updatedGame.player1.score);
    //     setPlayer2Score(updatedGame.player2.score);
    //
    //     setPlayer1Lamas(updatedGame.player1.lamas);
    //     setPlayer2Lamas(updatedGame.player2.lamas);
    //
    //     setCurrentPlayer(updatedGame.currentPlayerName === updatedGame.player1.name ? 1 : 2);
    //     setWinner(updatedGame.winner);
    // };
    //

    return (
        <div className="game-page">

            {/*//темна тема(верхнє закоментувати)*/}
            {/* <div className={darkTheme ? "game-page dark-theme" : "game-page"}>*/}
            {/*//*/}

            <Link className="back-button" to="/">
                ← Back to Menu
            </Link>

                {/* темна тема */}
                {/*<button*/}
                {/*    className="theme-button"*/}
                {/*    onClick={() => setDarkTheme(!darkTheme)}*/}
                {/*>*/}
                {/*    {darkTheme ? "Light Theme" : "Dark Theme"}*/}
                {/*</button>*/}
                {/* */}

            <h1 className="game-title">Slide-A-Lama</h1>

            {winner && <h2 className="winner-text">Winner: {winner}</h2>}


            {/* анімація перемоги , замінити на те що зверху на 1 рядок*/}
            {/*{winner && (*/}
            {/*    <div className="winner-animation">*/}
            {/*        <h2 className="winner-text">Winner: {winner}</h2>*/}
            {/*        <div className="confetti">🎉 🎊 🦙 🎉 🎊</div>*/}
            {/*    </div>*/}
            {/*)}*/}
            {/* */}

            {/* таймер */}
            {/*<h2 className="winner-text">*/}
            {/*    Time left: {timeLeft}s*/}
            {/*</h2>*/}
            {/* */}

            {/* таймер + запис часу гри в score */}
            {/*<h2 className="winner-text">*/}
            {/*    Game time: {gameTime}s*/}
            {/*</h2>*/}
            {/* */}

            {/* підказка */}
            {/*{hint && <h2 className="winner-text">{hint}</h2>}*/}
            {/* */}

            <div className="game-layout">
                <div className="players-column">
                    <PlayerPanel
                        name={
                            currentPlayer === 1
                                ? `${gameRequest?.player1Name ?? "Player 1"} - Turn`
                                : gameRequest?.player1Name ?? "Player 1"
                        }
                        score={player1Score}
                        lamas={player1Lamas}
                    />

                    <PlayerPanel
                        name={currentPlayer === 2 ? `${opponentName} - Turn` : opponentName}
                        score={player2Score}
                        lamas={player2Lamas}
                    />
                </div>

                <div className="game-center">
                    <Board board={board} />

                    {/* поміняти плити місцями , верхнєзакоментувати*/}
                    {/*<Board*/}
                    {/*    board={board}*/}
                    {/*    onTileClick={handleTileClick}*/}
                    {/*    selectedTile={selectedTile}*/}
                    {/*/>*/}
                    {/* */}
                    <NextTile type={nextTile} />
                </div>

                <div className="right-column">
                    <ScoreGuide />
                    <MoveControls onMove={handleMove} />

                    {/*//крок назад*/}
                    {/*<MoveControls onMove={handleMove} onUndo={handleUndo} />*/}
                    {/*//*/}

                    {/*//підсказка*/}
                    {/*<MoveControls onMove={handleMove} onHint={handleHint} />*/}
                    {/*//*/}

                    {/*//бомба*/}
                    {/*<MoveControls onMove={handleMove} onBomb={handleBomb} />*/}
                    {/*//*/}

                </div>
            </div>
        </div>
    );
}
