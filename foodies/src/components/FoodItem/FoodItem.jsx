import React, { useContext } from "react";
import { useNavigate } from "react-router-dom";
import { StoreContext } from "../../context/StoreContext";

export default function FoodItem({ food }) {
	const { increaseQty, decreaseQty, quantities } = useContext(StoreContext);
	const navigate = useNavigate();

	const goToDetails = () => {
		navigate(`/food/${food.id}`);
	};

	const handleIncrease = (e) => {
		e.stopPropagation();
		increaseQty(food.id);
	};

	const handleDecrease = (e) => {
		e.stopPropagation();
		decreaseQty(food.id);
	};

	return (
		<div className="col-12 col-sm-6 col-md-4 col-lg-3 mb-4 d-flex justify-content-center">
			<div
				className="card h-100"
				style={{ width: "18rem", cursor: "pointer" }}
				onClick={goToDetails}
			>
				<img
					src={food.imageUrl}
					alt={food.name}
					className="card-img-top img-fluid object-fit-cover"
					style={{ height: "220px", objectPosition: "center" }}
				/>

				<div className="card-body d-flex flex-column">
					<h5 className="card-title text-start">{food.name}</h5>
					<p className="card-text text-muted text-start">{food.description}</p>

					<div className="mt-auto">
						<div className="d-flex justify-content-between align-items-center mb-2">
							<span className="h5 mb-0">₹{food.price}</span>
							<div>
								<i className="bi bi-star-fill text-warning"></i>
								<i className="bi bi-star-fill text-warning"></i>
								<i className="bi bi-star-fill text-warning"></i>
								<i className="bi bi-star-fill text-warning"></i>
								<i className="bi bi-star-half text-warning"></i>
								<small className="text-muted ms-1">(4.5)</small>
							</div>
						</div>

						<div className="d-flex justify-content-between">
							<button
								className="btn btn-primary btn-sm w-100 me-2"
								onClick={(e) => {
									e.stopPropagation();
									goToDetails();
								}}
							>
								View Food
							</button>

							{quantities[food.id] > 0 ? (
								<div className="d-flex align-items-center gap-2">
									<button
										className="btn btn-danger btn-sm"
										onClick={handleDecrease}
									>
										<i className="bi bi-dash-circle"></i>
									</button>
									<span className="fw-bold">{quantities[food.id]}</span>
									<button
										className="btn btn-success btn-sm"
										onClick={handleIncrease}
									>
										<i className="bi bi-plus-circle"></i>
									</button>
								</div>
							) : (
								<button
									className="btn btn-primary btn-sm"
									onClick={handleIncrease}
								>
									<i className="bi bi-plus-circle"></i>
								</button>
							)}
						</div>
					</div>
				</div>
			</div>
		</div>
	);
}
