import React, { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { fetchFoodDetails } from "../../service/foodService";
import {StoreContext} from'../../context/StoreContext'

function FoodDetails() {
	const { id } = useParams();
	const navigate=useNavigate();
    const[data,setData]=useState({})
	const{increaseQty}=useContext(StoreContext)

    useEffect(()=>{
        const loadFoodDetails=async(id)=>{
            const foodData=await fetchFoodDetails(id);
            console.log(foodData)
            setData(foodData);
        }
        loadFoodDetails(id)
    },[id])

	const addToCart=()=>{
		increaseQty(data.id);
		navigate('/cart')
	}
	
	if(!data.name) return <div className="container py-5 text-center min-vh-100"><div className="spinner-border text-primary" role="status"></div></div>;

	return (
		<section className="py-5 min-vh-100">
			<div className="container px-4 px-lg-5 my-5 glass-panel p-5">
				<button className="btn btn-glass mb-4 hover-scale rounded-pill px-4" onClick={() => navigate(-1)}>
					<i className="bi bi-arrow-left me-2"></i> Back
				</button>
				<div className="row gx-4 gx-lg-5 align-items-center">
					<div className="col-md-6 mb-5 mb-md-0 d-flex justify-content-center">
						<img
							className="rounded-4 shadow-lg object-fit-cover hover-lift border border-secondary border-opacity-50"
							src={data.imageUrl}
							alt={data.name}
							style={{maxHeight: '500px', width: '100%'}}
						/>
					</div>
					<div className="col-md-6 ps-lg-5">
						<div className="mb-2">
							<span className="badge bg-primary fs-6 px-3 py-2 rounded-pill shadow-sm">{data.category}</span>
						</div>
						<h1 className="display-4 fw-bold mt-2 mb-3">{data.name}</h1>
						
						<div className="d-flex align-items-center mb-4">
							<div className="fs-2 fw-bold text-gradient me-4">
								&#8377;{data.price}.00
							</div>
							<div className="d-flex align-items-center bg-glass px-3 py-1 rounded-pill border border-secondary">
								<i className="bi bi-star-fill text-warning me-1"></i>
								<i className="bi bi-star-fill text-warning me-1"></i>
								<i className="bi bi-star-fill text-warning me-1"></i>
								<i className="bi bi-star-fill text-warning me-1"></i>
								<i className="bi bi-star-half text-warning me-2"></i>
								<span className="text-light fw-semibold">4.5 (120 reviews)</span>
							</div>
						</div>
						
						<div className="pe-lg-5 mb-5">
							<h5 className="fw-semibold mb-3 text-light">Description</h5>
							<p className="lead text-muted fs-5 lh-lg">
								{data.description}
							</p>
						</div>
						
						<div className="d-flex gap-3">
							<button className="btn btn-gradient btn-lg px-5 hover-scale rounded-pill flex-grow-1 shadow-lg" type="button" onClick={addToCart}>
								<i className="bi bi-cart-fill me-2"></i> Add to cart
							</button>
							<button className="btn btn-glass btn-lg px-4 hover-scale rounded-pill" type="button">
								<i className="bi bi-heart"></i>
							</button>
						</div>
					</div>
				</div>
			</div>
		</section>
	);
}

export default FoodDetails;
