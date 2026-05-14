import "./Tile.css";
import type { TileProps, TileType } from "../../types/game";

import banana from "../../tiles/banana.png";
import bar from "../../tiles/bar.png";
import bell from "../../tiles/bell.png";
import cherry from "../../tiles/cherry.png";
import pear from "../../tiles/pear.png";
import plum from "../../tiles/plum.png";
import seven from "../../tiles/seven.png";

const tileImages: Record<TileType, string> = {
    BANANA: banana,
    BAR: bar,
    BELL: bell,
    CHERRY: cherry,
    PEAR: pear,
    PLUM: plum,
    SEVEN: seven,
};

export default function Tile({ type, onClick }: TileProps) {
    return (
        <button className="tile" onClick={onClick}>
            <img className="tile-image" src={tileImages[type]} alt={type} />
        </button>
    );
}