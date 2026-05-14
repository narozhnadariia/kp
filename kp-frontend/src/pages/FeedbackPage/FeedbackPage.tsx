import { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";

import { submitFeedback } from "../../api/gameApi";

//фільтр коментарів
// import { submitFeedback, filterComments } from "../../api/gameApi";
//

import "./FeedbackPage.css";

type FeedbackState = {
    gameId: number;
    winner: string;
    player1Name: string;
    player2Name: string;
    player1Score: number;
    player2Score: number;

    //таймер + запис часу гри в score
    // gameTime: number;
    //
};

export default function FeedbackPage() {
    const location = useLocation();
    const navigate = useNavigate();

    const state = location.state as FeedbackState | null;

    const [rating, setRating] = useState<number>(5);
    const [comment, setComment] = useState("");

    // фільтр коментарів
    // const [filterText, setFilterText] = useState("");
    // const [comments, setComments] = useState<any[]>([]);
    //

    const [isSubmitted, setIsSubmitted] = useState(false);



    if (!state) {
        return (
            <main className="feedback-page">
                <section className="feedback-card">
                    <h1 className="feedback-title">No game result found</h1>

                    <Link className="feedback-button" to="/">
                        Back to Menu
                    </Link>
                </section>
            </main>
        );
    }

    const handleSubmit = async () => {
        await submitFeedback({
            gameId: state.gameId,
            winner: state.winner,
            player1Name: state.player1Name,
            player2Name: state.player2Name,
            player1Score: state.player1Score,
            player2Score: state.player2Score,
            rating,
            comment,

        });

        setIsSubmitted(true);
    };

//     // фільтр коментарів
//     const handleFilterComments = async () => {
//         const filteredComments = await filterComments(filterText);
//         setComments(filteredComments);
//     };
// //

    return (
        <main className="feedback-page">
            <section className="feedback-card">
                <div className="feedback-badge">Game finished</div>

                <h1 className="feedback-title">Winner: {state.winner}</h1>

                <div className="feedback-score-box">
                    <p>
                        {state.player1Name}: <strong>{state.player1Score}</strong> points
                    </p>

                    <p>
                        {state.player2Name}: <strong>{state.player2Score}</strong> points
                    </p>
                </div>

                {/* таймер + запис часу гри в score */}
                {/*<p>*/}
                {/*    Game time: <strong>{state.gameTime}</strong> seconds*/}
                {/*</p>*/}
                {/* */}

                {!isSubmitted ? (
                    <>
                        <div className="feedback-field">
                            <label>Rating</label>

                            <select
                                value={rating}
                                onChange={(e) => setRating(Number(e.target.value))}
                            >
                                <option value={1}>1 - Bad</option>
                                <option value={2}>2</option>
                                <option value={3}>3 - Okay</option>
                                <option value={4}>4</option>
                                <option value={5}>5 - Great</option>
                            </select>
                        </div>

                        <div className="feedback-field">
                            <label>Comment</label>

                            <textarea
                                value={comment}
                                onChange={(e) => setComment(e.target.value)}
                                placeholder="Write your comment..."
                            />
                        </div>

                        {/* фільтр коментарів */}
                        {/*<div className="feedback-field">*/}
                        {/*    <label>Filter comments</label>*/}

                        {/*    <input*/}
                        {/*        type="text"*/}
                        {/*        value={filterText}*/}
                        {/*        onChange={(e) => setFilterText(e.target.value)}*/}
                        {/*        placeholder="Search comment..."*/}
                        {/*    />*/}
                        {/*</div>*/}

                        {/*//фільтр коментарів*/}
                        {/*<button className="feedback-button" onClick={handleFilterComments}>*/}
                        {/*    Filter Comments*/}
                        {/*</button>*/}

                        {/*<div className="feedback-score-box">*/}
                        {/*    {comments.length === 0 ? (*/}
                        {/*        <p>No comments found</p>*/}
                        {/*    ) : (*/}
                        {/*        comments.map((comment, index) => (*/}
                        {/*            <p key={index}>*/}
                        {/*                <strong>{comment.player}</strong>: {comment.comment}*/}
                        {/*            </p>*/}
                        {/*        ))*/}
                        {/*    )}*/}
                        {/*</div>*/}

                        {/*//*/}




                        <button className="feedback-button" onClick={handleSubmit}>
                            Save Feedback
                        </button>
                    </>
                ) : (
                    <>
                        <h2 className="feedback-success">Feedback saved!</h2>

                        <button
                            className="feedback-button"
                            onClick={() => navigate("/")}
                        >
                            Back to Menu
                        </button>
                    </>
                )}
            </section>
        </main>
    );
}