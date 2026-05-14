import "./PlayerPanel.css";

type PlayerPanelProps = {
    name: string;
    score: number;
    lamas: number;
};

export default function PlayerPanel({ name, score, lamas }: PlayerPanelProps) {
    return (
        <div className="player-panel">
            <h2 className="player-name">{name}</h2>

            <div className="player-info">
                <span>Score:</span>
                <strong>{score}</strong>
            </div>

            <div className="player-info">
                <span>Lamas:</span>
                <strong>{lamas}</strong>
            </div>
        </div>
    );
}

//профіль гравця

// export default function PlayerPanel({ name, score, lamas }: PlayerPanelProps) {
//     const getLevel = () => {
//         if (score >= 300) return "Expert";
//         if (score >= 150) return "Medium";
//         return "Beginner";
//     };
//
//     return (
//         <div className="player-panel">
//             <h2 className="player-name">{name}</h2>
//
//             <div className="player-info">
//                 <p>
//                     Score: <strong>{score}</strong>
//                 </p>
//
//                 <p>
//                     Lamas: <strong>{lamas}</strong>
//                 </p>
//
//                 {/* профіль гравця */}
//                 <p>
//                     Level: <strong>{getLevel()}</strong>
//                 </p>
//                 {/* */}
//             </div>
//         </div>
//     );
// }
//