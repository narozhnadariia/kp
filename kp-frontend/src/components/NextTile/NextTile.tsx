import Tile from "../Tile/Tile";
import type { TileType } from "../../types/game";
import "./NextTile.css";

type NextTileProps = {
    type: TileType;
};

export default function NextTile({ type }: NextTileProps) {
    return (
        <div className="next-tile">
            <p className="next-tile-label">Next tile</p>
            <Tile type={type} />
        </div>
    );
}