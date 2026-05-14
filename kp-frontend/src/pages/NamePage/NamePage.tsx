import { useState } from "react";
import { Link, useNavigate, useSearchParams } from "react-router-dom";

import "./NamePage.css";

type BotDifficulty = "EASY" | "HARD";

export default function NamePage() {
    const [searchParams] = useSearchParams();
    const navigate = useNavigate();

    const mode =
        searchParams.get("mode") === "PLAYER_VS_BOT"
            ? "PLAYER_VS_BOT"
            : "PLAYER_VS_PLAYER";

    const isPlayerVsBot = mode === "PLAYER_VS_BOT";

    const [player1Name, setPlayer1Name] = useState("");
    const [player2Name, setPlayer2Name] = useState("");
    const [botDifficulty, setBotDifficulty] = useState<BotDifficulty | null>(null);

    const canStart = isPlayerVsBot
        ? player1Name.trim().length >= 2 && botDifficulty !== null
        : player1Name.trim().length >= 2 && player2Name.trim().length >= 2;

    // гість без імені
    // const canStart = isPlayerVsBot
    //     ? botDifficulty !== null
    //     : true;
    //

    const handleStart = () => {
        if (!canStart) return;

        navigate("/game", {
            state: {
                gameMode: mode,
                player1Name: player1Name.trim(),
                player2Name: isPlayerVsBot ? null : player2Name.trim(),
                botDifficulty: isPlayerVsBot ? botDifficulty : null,
            },

            // гість без імені
            // state: {
            //     gameMode: mode,
            //     player1Name: player1Name.trim() === "" ? "Guest 1" : player1Name.trim(),
            //     player2Name: isPlayerVsBot
            //         ? null
            //         : player2Name.trim() === "" ? "Guest 2" : player2Name.trim(),
            //     botDifficulty: isPlayerVsBot ? botDifficulty : null,
            // },
            //

        });
    };

    return (
        <main className="name-page">
            <Link className="name-back-button" to="/">
                ← Back to Menu
            </Link>

            <section className="name-card">
                <div className="name-badge">Game setup</div>

                <h1 className="name-title">Slide-A-Lama</h1>

                <p className="name-description">
                    Enter player names and choose your opponent before starting the game.
                </p>

                <div className="name-form">
                    <div className="name-field">
                        <label>Player 1 name</label>
                        <input
                            type="text"
                            value={player1Name}
                            onChange={(e) => setPlayer1Name(e.target.value)}
                            placeholder="Alex"
                        />
                    </div>

                    {!isPlayerVsBot && (
                        <div className="name-field">
                            <label>Player 2 name</label>
                            <input
                                type="text"
                                value={player2Name}
                                onChange={(e) => setPlayer2Name(e.target.value)}
                                placeholder="Mia"
                            />
                        </div>
                    )}

                    {isPlayerVsBot && (
                        <div className="name-field">
                            <label>Bot difficulty</label>

                            <div className="difficulty-group">
                                <label className="difficulty-option">
                                    <input
                                        type="radio"
                                        name="botDifficulty"
                                        checked={botDifficulty === "EASY"}
                                        onChange={() => setBotDifficulty("EASY")}
                                    />
                                    Easy
                                </label>

                                <label className="difficulty-option">
                                    <input
                                        type="radio"
                                        name="botDifficulty"
                                        checked={botDifficulty === "HARD"}
                                        onChange={() => setBotDifficulty("HARD")}
                                    />
                                    Hard
                                </label>
                            </div>
                        </div>
                    )}
                </div>

                <button
                    className="name-start-button"
                    disabled={!canStart}
                    onClick={handleStart}
                >
                    Start Game
                </button>
            </section>
        </main>
    );
}