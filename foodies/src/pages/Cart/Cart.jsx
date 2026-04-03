import React, { useContext } from "react";
import { StoreContext } from "../../context/StoreContext";
import './Cart.css'
import { Link, useNavigate } from "react-router-dom";
import {calculateCartTotals} from '../../util/cartUtils'

function Cart() {
	const navigate=useNavigate()
	const { foodList, increaseQty, decreaseQty, quantities,removeFromCart } =
		useContext(StoreContext);
	//cart item
	const cartItems = foodList.filter((food) => quantities[food.id] > 0);

	const{total,tax,subTotal,shipping}=calculateCartTotals(cartItems,quantities)
	
	return (
		<div className="container py-5 min-vh-100">
			<h1 className="mb-5 display-5 fw-bold text-gradient">Your Shopping Cart</h1>
			<div className="row g-5">
				<div className="col-lg-8">
					{cartItems.length === 0 ? (
						<div className="glass-panel p-5 text-center">
							<i className="bi bi-cart-x display-1 text-muted mb-3"></i>
							<h3 className="text-light">Your Cart is Empty</h3>
							<p className="text-muted mb-4">Looks like you haven't added any items to your cart yet.</p>
							<Link to="/explore" className="btn btn-gradient btn-lg">
								Explore Menu
							</Link>
						</div>
					) : (
						<div className="glass-panel p-0 overflow-hidden mb-4 border-0">
							<div className="card-body p-4">
								{cartItems.map((food, idx) => (
									<React.Fragment key={food.id}>
										<div className="row cart-item align-items-center mb-3">
											<div className="col-md-2">
												<img
													src={food.imageUrl}
													alt={food.name}
													className="img-fluid rounded-3 object-fit-cover shadow-sm"
													style={{width: '100px', height: '100px'}}
												/>
											</div>
											<div className="col-md-5">
												<h5 className="card-title fw-bold mb-1 fs-5">{food.name}</h5>
												<span className="badge bg-glass border border-secondary text-light mb-2">{food.category}</span>
											</div>
											<div className="col-md-3">
												<div className="d-flex align-items-center gap-2 bg-glass rounded-pill px-2 py-1 border border-secondary" style={{width: 'fit-content'}}>
													<button
														className="btn rounded-circle p-0 text-light d-flex align-items-center justify-content-center hover-scale"
														style={{width: '30px', height: '30px'}}
														type="button"
														onClick={() => decreaseQty(food.id)}
													>
														<i className="bi bi-dash"></i>
													</button>
													<span className="fw-bold fs-6 px-2">{quantities[food.id]}</span>
													<button
														className="btn rounded-circle p-0 text-light d-flex align-items-center justify-content-center hover-scale"
														style={{width: '30px', height: '30px'}}
														type="button"
														onClick={() => increaseQty(food.id)}
													>
														<i className="bi bi-plus"></i>
													</button>
												</div>
											</div>
											<div className="col-md-2 text-end d-flex flex-column align-items-end justify-content-center">
												<p className="fw-bold fs-5 text-gradient mb-2">
													&#8377;{(food.price * quantities[food.id]).toFixed(2)}
												</p>
												<button className="btn btn-sm btn-glass text-danger hover-scale px-3 rounded-pill" onClick={()=>removeFromCart(food.id)}>
													<i className="bi bi-trash"></i> Remove
												</button>
											</div>
										</div>
										{idx !== cartItems.length - 1 && <hr className="border-secondary opacity-25 my-4" />}
									</React.Fragment>
								))}
							</div>
						</div>
					)}
					{cartItems.length > 0 && (
						<div className="text-start mb-4">
							<Link to="/" className="btn btn-glass px-4 py-2 hover-scale rounded-pill">
								<i className="bi bi-arrow-left me-2"></i>Continue Shopping
							</Link>
						</div>
					)}
				</div>
				<div className="col-lg-4">
					<div className="glass-panel p-4 sticky-top" style={{top: '100px'}}>
						<h4 className="fw-bold mb-4 pb-2 border-bottom border-light border-opacity-10">Order Summary</h4>
						<div className="d-flex justify-content-between mb-3 text-muted">
							<span>Subtotal</span>
							<span className="text-light">&#8377;{subTotal.toFixed(2)}</span>
						</div>
						<div className="d-flex justify-content-between mb-3 text-muted">
							<span>Shipping Fee</span>
							<span className="text-light">&#8377;{subTotal === 0 ? '0.00' : shipping.toFixed(2)}</span>
						</div>
						<div className="d-flex justify-content-between mb-4 text-muted">
							<span>Estimated Tax</span>
							<span className="text-light">&#8377;{tax.toFixed(2)}</span>
						</div>
						
						<div className="p-3 bg-glass rounded-3 mb-4 border border-secondary border-opacity-50">
							<div className="d-flex justify-content-between align-items-center">
								<strong className="fs-5">Total</strong>
								<strong className="fs-4 text-gradient">&#8377;{subTotal === 0 ? '0.00' : total.toFixed(2)}</strong>
							</div>
						</div>
						
						<button
							className="btn btn-gradient w-100 py-3 fs-5 rounded-pill shadow-lg hover-scale"
							disabled={cartItems.length === 0}
							onClick={()=>navigate('/order')}
						>
							Proceed to Checkout <i className="bi bi-arrow-right ms-2"></i>
						</button>
					</div>
				</div>
			</div>
		</div>
	);
}

export default Cart;
