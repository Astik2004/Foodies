import React, { useContext } from "react";
import { useNavigate } from "react-router-dom";
import { StoreContext } from "../../context/StoreContext";
import './FoodItem.css';

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
		<div className="col-12 col-sm-6 col-md-4 col-xl-3 mb-4">
			<div
				className="card h-100 glass-panel border-0 overflow-hidden food-card hover-lift"
				style={{ cursor: "pointer" }}
				onClick={goToDetails}
			>
				<div className="position-relative overflow-hidden">
					<img
						src={food.imageUrl}
						alt={food.name}
						className="card-img-top w-100 object-fit-cover food-img"
						style={{ height: "240px", objectPosition: "center" }}
					/>
					<div className="price-tag position-absolute top-0 end-0 m-3 px-3 py-1 rounded-pill fw-bold shadow-lg">
						₹{food.price}
					</div>
				</div>

				<div className="card-body d-flex flex-column p-4">
					<div className="d-flex justify-content-between align-items-start mb-2">
						<h5 className="card-title text-start fw-bold fs-4 mb-0 text-truncate me-2">{food.name}</h5>
						<div className="rating-badge bg-glass px-2 py-1 rounded d-flex align-items-center">
							<i className="bi bi-star-fill text-warning me-1" style={{fontSize: '0.8rem'}}></i>
							<small className="fw-semibold">4.5</small>
						</div>
					</div>
					
					<p className="card-text text-secondary text-start fs-6 mb-4">
						{food.description}
					</p>

					<div className="mt-3">
						{quantities[food.id] > 0 ? (
							<div className="d-flex align-items-center justify-content-between bg-white border border-primary rounded-3 shadow-sm px-2 py-1 mx-auto" style={{maxWidth: '120px'}}>
								<button
									className="btn btn-sm p-1 text-primary d-flex align-items-center justify-content-center fw-bold"
									onClick={handleDecrease}
								>
									<i className="bi bi-dash"></i>
								</button>
								<span className="fw-bold fs-6 text-primary">{quantities[food.id]}</span>
								<button
									className="btn btn-sm p-1 text-primary d-flex align-items-center justify-content-center fw-bold"
									onClick={handleIncrease}
								>
									<i className="bi bi-plus"></i>
								</button>
							</div>
						) : (
							<button
								className="btn btn-outline-primary fw-bold px-4 rounded-3 d-block mx-auto shadow-sm"
								onClick={handleIncrease}
							>
								ADD
							</button>
						)}
					</div>
				</div>
			</div>
		</div>
	);
}
