import React from "react";
import { assets } from "../../assets/assets";
import { useState } from "react";
import { addFood } from "../../services/foodService";
import { toast } from "react-toastify";
import "./AddFood.css";
const AddFood = () => {
	const [image, setImage] = useState(false);
	const [data, setData] = useState({
		name: "",
		description: "",
		category: "",
		price: "",
	});
	const onChangeHandler = (e) => {
		setData({ ...data, [e.target.name]: e.target.value });
	};
	const handleOnSubmit = async (e) => {
		e.preventDefault();
		if (!image) {
			toast.error("Please upload an image");
			return;
		}
		try {
			await addFood(data, image);
			toast.success("Food added successfully");
			setData({
				name: "",
				description: "",
				category: "",
				price: "",
			});
			setImage(null);
		} catch (error) {
			console.error("Error adding food:", error);
			toast.error("Failed to add food. Please try again.");
			throw error;
		}
	};
	return (
		<div className="container mt-3">
			<div className="row justify-content-center">
				<div className="col-md-6">
					<div className="card shadow-sm">
						<div className="card-body">
							<h4 className="mb-3 fw-bold text-center">Add Food Item 🍽️</h4>

							<form onSubmit={(e) => handleOnSubmit(e)}>
								{/* Image Upload */}
								<div className="text-center mb-3">
									<label htmlFor="image" style={{ cursor: "pointer" }}>
										<img
											src={image ? URL.createObjectURL(image) : assets.upload}
											alt="upload"
											className="rounded border p-1"
											style={{ width: 120, height: 120, objectFit: "cover" }}
										/>
									</label>
									<input
										type="file"
										id="image"
										hidden
										required
										onChange={(e) => setImage(e.target.files[0])}
									/>
									<small className="text-muted d-block mt-1">
										Click image to upload
									</small>
								</div>

								{/* Name */}
								<div className="form-floating mb-2">
									<input
										type="text"
										className="form-control"
										id="name"
										name="name"
										placeholder="Pizza"
										value={data.name}
										onChange={onChangeHandler}
										required
									/>
									<label htmlFor="name">Food Name</label>
								</div>

								{/* Description */}
								<div className="form-floating mb-2">
									<textarea
										className="form-control"
										id="description"
										name="description"
										placeholder="Description"
										style={{ height: "60px" }}
										onChange={onChangeHandler}
										value={data.description}
										required
									></textarea>
									<label>Description</label>
								</div>

								{/* Category */}
								<div className="form-floating mb-2">
									<select
										className="form-control"
										id="category"
										name="category"
										onChange={onChangeHandler}
										value={data.category}
										required
									>
										<option value="">Select Category</option>
										<option>Pizza</option>
										<option>Burger</option>
										<option>Sushi</option>
										<option>Dessert</option>
										<option>Cake</option>
										<option>Rolls</option>
										<option>Ice cream</option>
										<option>Salad</option>
										<option>Biryani</option>
									</select>
									<label>Category</label>
								</div>

								{/* Price */}
								<div className="form-floating mb-3">
									<input
										type="number"
										className="form-control"
										id="price"
										name="price"
										placeholder="200"
										value={data.price}
										onChange={onChangeHandler}
										required
									/>
									<label htmlFor="price">Price (₹)</label>
								</div>

								<button className="btn btn-success w-100 py-2 fw-bold">
									✅ Add Food
								</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};

export default AddFood;
