import { Link } from "react-router-dom";
import "./HomePage.css";

export default function HomePage() {
    return (
        <main className="home-page">
            <section className="home-card">
                <div className="home-badge">Welcome to the game</div>

                <h1 className="home-title">Slide-A-Lama</h1>

                <p className="home-description">
                    Match tiles, collect points and steal lamas from your opponent.
                    The player who collects all lamas wins.
                </p>

                <div className="home-rules">
                    <p> 3 same tiles = base points</p>
                    <p> 4 same tiles = points x2</p>
                    <p> 5 same tiles = points x3</p>
                    <p> Every 70 points = take 1 lama</p>
                </div>

                <div className="home-buttons">
                    <Link className="start-button" to="/names?mode=PLAYER_VS_PLAYER">
                        Play with Friend
                    </Link>

                    <Link className="start-button bot-button" to="/names?mode=PLAYER_VS_BOT">
                        Play with Bot
                    </Link>
                    {/*лідер борд*/}
                    {/*<Link className="start-button" to="/leaderboard">*/}
                    {/*    Leaderboard*/}
                    {/*</Link>*/}
                    {/*//*/}
                </div>
            </section>
        </main>
    );
}