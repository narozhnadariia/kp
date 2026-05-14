import type { MoveDirection } from "../../types/game";
import "./MoveControls.css";

type MoveControlsProps = {
    onMove: (direction: MoveDirection, index: number) => void;
    //крок назад
    // onUndo: () => void;
    //

    // підказка
    // onHint?: () => void;
    //

    // бомба
    // onBomb?: () => void;
    //
};

//крок назад
// export default function MoveControls({ onMove, onUndo }: MoveControlsProps) {
//

//підсказка
// export default function MoveControls({ onMove, onHint }: MoveControlsProps) {
//

//бомба
// export default function MoveControls({ onMove, onBomb }: MoveControlsProps) {
//

export default function MoveControls({ onMove }: MoveControlsProps) {
    return (
        <div className="move-controls">
            <div className="move-section">
                <p className="move-title">Columns</p>

                <div className="move-buttons">

                    {/*//збільшити мапу*/}
                    {/*{[0, 1, 2, 3, 4].map((colIndex) => (*/}
                    {/*//*/}

                    {[0, 1, 2, 3, 4].map((colIndex) => (

                        <button
                            key={`top-${colIndex}`}
                            className="move-button"
                            onClick={() => onMove("TOP", colIndex)}
                        >
                            T{colIndex + 1}
                        </button>
                    ))}
                </div>
            </div>

            <div className="move-section">
                <p className="move-title">Rows</p>

                {/*//збільшити мапу*/}
                {/*{[0, 1, 2, 3, 4].map((rowIndex) => (*/}
                {/*//*/}

                {[0, 1, 2, 3, 4].map((rowIndex) => (

                    <div className="row-controls" key={`row-${rowIndex}`}>
                        <span>Row {rowIndex + 1}</span>

                        <button
                            className="move-button"
                            onClick={() => onMove("LEFT", rowIndex)}
                        >
                            L
                        </button>

                        <button
                            className="move-button"
                            onClick={() => onMove("RIGHT", rowIndex)}
                        >
                            R
                        </button>
                    </div>
                ))}
            </div>
            {/*//крок назад*/}
            {/*<div className="move-section">*/}
            {/*    <button className="move-button" onClick={onUndo}>*/}
            {/*        Undo*/}
            {/*    </button>*/}
            {/*</div>*/}
            {/*//*/}

            {/* підказка */}
            {/*{onHint && (*/}
            {/*    <div className="move-section">*/}
            {/*        <button className="move-button" onClick={onHint}>*/}
            {/*            Hint*/}
            {/*        </button>*/}
            {/*    </div>*/}
            {/*)}*/}
            {/* */}

            {/* бомба */}
            {/*{onBomb && (*/}
            {/*    <div className="move-section">*/}
            {/*        <button className="move-button" onClick={onBomb}>*/}
            {/*            Bomb*/}
            {/*        </button>*/}
            {/*    </div>*/}
            {/*)}*/}
            {/* */}

        </div>
    );
}