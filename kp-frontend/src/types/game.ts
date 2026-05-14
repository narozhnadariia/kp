export type TileType =
    | "SEVEN"
    | "BAR"
    | "CHERRY"
    | "PEAR"
    | "PLUM"
    | "BANANA"
    | "BELL";

export type MoveDirection = "LEFT" | "RIGHT" | "TOP";

export type TileProps = {
    type: TileType;
    onClick?: () => void;
};

export const ALL_TILES: TileType[] = [
    "SEVEN",
    "BAR",
    "CHERRY",
    "PEAR",
    "PLUM",
    "BANANA",
    "BELL",
];

export type FeedbackRequest = {
    gameId: number;
    winner: string;
    player1Name: string;
    player2Name: string;
    player1Score: number;
    player2Score: number;
    rating: number;
    comment: string;
};

