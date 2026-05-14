import { BrowserRouter, Routes, Route } from "react-router-dom";

import HomePage from "./pages/HomePage/HomePage";
import NamePage from "./pages/NamePage/NamePage";
import GamePage from "./pages/GamePage/GamePage";
import FeedbackPage from "./pages/FeedbackPage/FeedbackPage";
//лідер борд
// import LeaderboardPage from "./pages/LeaderboardPage/LeaderboardPage";
//

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/names" element={<NamePage />} />
                <Route path="/game" element={<GamePage />} />
                <Route path="/feedback" element={<FeedbackPage />} />
                {/*лідер борд*/}
                {/*<Route path="/leaderboard" element={<LeaderboardPage />} />*/}
                {/*//*/}
            </Routes>
        </BrowserRouter>
    );
}