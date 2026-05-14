import "./ScoreGuide.css";

export default function ScoreGuide() {
    return (
        <div className="score-guide">
            <h2 className="score-guide-title">Scoring</h2>

            <div className="score-line"><span>7 7 7</span><strong>150</strong></div>
            <div className="score-line"><span>BAR BAR BAR</span><strong>100</strong></div>
            <div className="score-line"><span>CHERRY</span><strong>70</strong></div>
            <div className="score-line"><span>PEAR</span><strong>40</strong></div>
            <div className="score-line"><span>PLUM</span><strong>30</strong></div>
            <div className="score-line"><span>BANANA</span><strong>20</strong></div>
            <div className="score-line"><span>BELL</span><strong>10</strong></div>

            <p className="score-note">4 of a kind x2</p>
            <p className="score-note">5 of a kind x3</p>
            <p className="score-note">Every 70 points = 1 lama</p>
        </div>
    );
}