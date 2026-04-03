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
					<div className="price-tag position-absolute top-0 end-0 m-3 px-3 py-1 rounded-pill fw-bold shadow-lg text-white">
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
					
					<p className="card-text text-muted text-start fs-6 mb-4 line-clamp-2">
						{food.description}
					</p>

					<div className="mt-auto d-flex justify-content-between align-items-center">
						<button
							className="btn btn-glass flex-grow-1 me-3 hover-scale"
							onClick={(e) => {
								e.stopPropagation();
								goToDetails();
							}}
						>
							View
						</button>

						{quantities[food.id] > 0 ? (
							<div className="d-flex align-items-center gap-2 bg-glass rounded-pill px-2 py-1 border border-secondary shadow-sm">
								<button
									className="btn rounded-circle p-0 text-danger d-flex align-items-center justify-content-center hover-scale"
									style={{width: '32px', height: '32px'}}
									onClick={handleDecrease}
								>
									<i className="bi bi-dash fs-5"></i>
								</button>
								<span className="fw-bold fs-5 px-1">{quantities[food.id]}</span>
								<button
									className="btn rounded-circle p-0 text-success d-flex align-items-center justify-content-center hover-scale"
									style={{width: '32px', height: '32px'}}
									onClick={handleIncrease}
								>
									<i className="bi bi-plus fs-5"></i>
								</button>
							</div>
						) : (
							<button
								className="btn btn-gradient rounded-circle p-0 d-flex align-items-center justify-content-center hover-scale shadow"
								style={{width: '45px', height: '45px'}}
								onClick={handleIncrease}
							>
								<i className="bi bi-plus-lg fs-5"></i>
							</button>
						)}
					</div>
				</div>
			</div>
		</div>
	);
}
