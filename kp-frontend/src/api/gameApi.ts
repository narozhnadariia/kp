import type { MoveDirection } from "../types/game";

const API_URL = "http://localhost:8080/api/games";

export type CreateGameRequest = {
    gameMode: "PLAYER_VS_PLAYER" | "PLAYER_VS_BOT";
    player1Name: string;
    player2Name: string | null;
    botDifficulty: "EASY" | "HARD" | null;
};

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

export async function createGame(request: CreateGameRequest) {
    const response = await fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
    });

    if (!response.ok) {
        const errorText = await response.text();
        console.log("Create game failed:", response.status, errorText);
        throw new Error(`Failed to create game: ${response.status}`);
    }

    return response.json();
}

export async function makeMove(
    gameId: number,
    direction: MoveDirection,
    index: number
) {
    const response = await fetch(`${API_URL}/${gameId}/move`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            direction,
            index,
        }),
    });

    if (!response.ok) {
        throw new Error("Failed to make move");
    }

    return response.json();
}



export async function submitFeedback(request: FeedbackRequest) {
    const response = await fetch(`${API_URL}/feedback`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
    });

    if (!response.ok) {
        throw new Error("Failed to submit feedback");
    }
}
//крок назад
// export async function undoMove(gameId: number) {
//     const response = await fetch(`${API_URL}/${gameId}/undo`, {
//         method: "POST",
//     });
//
//     if (!response.ok) {
//         throw new Error("Failed to undo move");
//     }
//
//     return response.json();
// }
//

// бомба
// export async function activateBomb(gameId: number) {
//     const response = await fetch(`${API_URL}/${gameId}/bomb`, {
//         method: "POST",
//     });
//
//     if (!response.ok) {
//         throw new Error("Failed to use bomb");
//     }
//
//     return response.json();
// }
//

// фільтр коментарів
// export async function filterComments(text: string) {
//     const response = await fetch(
//         `${API_URL.replace("/games", "/comments")}/filter?game=Slide-A-Lama&text=${encodeURIComponent(text)}`
//     );
//
//     if (!response.ok) {
//         throw new Error("Failed to filter comments");
//     }
//
//     return response.json();
// }
//


// поміняти плити місцями
// export async function swapTiles(
//     gameId: number,
//     row1: number,
//     col1: number,
//     row2: number,
//     col2: number
// ) {
//     const response = await fetch(`${API_URL}/${gameId}/swap`, {
//         method: "POST",
//         headers: {
//             "Content-Type": "application/json",
//         },
//         body: JSON.stringify({
//             row1,
//             col1,
//             row2,
//             col2,
//         }),
//     });
//
//     if (!response.ok) {
//         throw new Error("Failed to swap tiles");
//     }
//
//     return response.json();
// }
//

// leaderboard
// export async function getLeaderboard() {
//     const response = await fetch("http://localhost:8080/api/score/leaderboard");
//
//     if (!response.ok) {
//         throw new Error("Failed to load leaderboard");
//     }
//
//     return response.json();
// }
//