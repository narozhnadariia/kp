import Tile from "../Tile/Tile";
import type { TileType } from "../../types/game";
import "./Board.css";

type BoardProps = {
    board: TileType[][];
};

export default function Board({ board }: BoardProps) {
    return (
        <div className="board">
            {board.map((row, rowIndex) =>
                row.map((tile, colIndex) => (
                    <Tile key={`${rowIndex}-${colIndex}`} type={tile} />
                ))
            )}
        </div>
    );
}

//поміняти плити місцями
// import Tile from "../Tile/Tile";
// import type { TileType } from "../../types/game";
// import "./Board.css";
//
// type BoardProps = {
//     board: TileType[][];
//
//     // поміняти плити місцями
//     onTileClick?: (row: number, col: number) => void;
//     selectedTile?: {
//         row: number;
//         col: number;
//     } | null;
//     //
// };
//
// export default function Board({ board, onTileClick, selectedTile }: BoardProps) {
//     return (
//         <div className="board">
//             {board.map((row, rowIndex) =>
//                 row.map((tile, colIndex) => {
//                     const isSelected =
//                         selectedTile?.row === rowIndex &&
//                         selectedTile?.col === colIndex;
//
//                     return (
//                         <button
//                             key={`${rowIndex}-${colIndex}`}
//                             className={
//                                 isSelected
//                                     ? "board-tile selected-tile"
//                                     : "board-tile"
//                             }
//                             onClick={() => {
//                                 if (onTileClick) {
//                                     onTileClick(rowIndex, colIndex);
//                                 }
//                             }}
//                         >
//                             <Tile type={tile} />
//                         </button>                    );
//                 })
//             )}
//         </div>
//     );
// }