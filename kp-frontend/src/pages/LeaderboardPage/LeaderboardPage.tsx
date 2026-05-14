// import { useEffect, useState } from "react";
// import { Link } from "react-router-dom";
//
// import { getLeaderboard } from "../../api/gameApi";
//
// import "./LeaderboardPage.css";
//
// type Score = {
//     player: string;
//     game: string;
//     points: number;
//     playedOn: string;
// };
//
// export default function LeaderboardPage() {
//     const [scores, setScores] = useState<Score[]>([]);
//
//     useEffect(() => {
//         async function loadLeaderboard() {
//             const data = await getLeaderboard();
//             setScores(data);
//         }
//
//         loadLeaderboard();
//     }, []);
//
//     return (
//         <main className="leaderboard-page">
//             <Link className="leaderboard-back-button" to="/">
//                 ← Back to Menu
//             </Link>
//
//             <section className="leaderboard-card">
//                 <div className="leaderboard-badge">Top players</div>
//
//                 <h1 className="leaderboard-title">Leaderboard</h1>
//
//                 <div className="leaderboard-list">
//                     {scores.length === 0 ? (
//                         <p>No scores yet</p>
//                     ) : (
//                         scores.map((score, index) => (
//                             <div className="leaderboard-row" key={index}>
//                                 <span>#{index + 1}</span>
//                                 <span>{score.player}</span>
//                                 <strong>{score.points} pts</strong>
//                             </div>
//                         ))
//                     )}
//                 </div>
//             </section>
//         </main>
//     );
// }