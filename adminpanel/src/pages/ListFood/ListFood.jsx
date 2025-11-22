import React, { useEffect, useState } from "react";
import { toast } from "react-toastify";
import "./ListFood.css";
import { deleteFood, listFood } from "../../services/foodService";

const ListFood = () => {
	const [list, setList] = useState([]);
	const fetchList = async () => {
		try {
			const data = await listFood();
			setList(data);
		} catch (error) {
			console.error("Error fetching food list:", error);
			toast.error("Failed to fetch food list");
		}
	};
	const removeFood = async (id) => {
		try {
			const success = await deleteFood(id);
			if (success) {
				toast.success("Food item deleted successfully");
				fetchList();
			} else {
				toast.error("Failed to delete food item");
			}
		} catch (error) {
			console.error("Error deleting food item:", error);
			toast.error("Failed to delete food item");
		}
	};
	useEffect(() => {
		fetchList();
	}, []);

	return (
		<div className="pu-5 row justify-content-center">
			<div className="col-11 card mt-2">
				<table className="table">
					<thead>
						<tr>
							<th>Image</th>
							<th>Name</th>
							<th>Category</th>
							<th>Price</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						{list.map((food, index) => (
							<tr key={index}>
								<td>
									<img
										src={food.imageUrl}
										alt={food.name}
										height={48}
										width={48}
									/>
								</td>
								<td>{food.name}</td>
								<td>{food.category}</td>
								<td>&#8377;{food.price}.00</td>
								<td className="text-danger">
									<i
										className="bi bi-x-circle-fill fs-2"
                    style={{ fontSize: "20px", cursor: "pointer", transition: "0.1s" }}
                    onMouseEnter={(e) => (e.target.style.transform = "scale(1.3)")}
                    onMouseLeave={(e) => (e.target.style.transform = "scale(1)")}
										onClick={() => removeFood(food.id)}
									></i>
								</td>
							</tr>
						))}
					</tbody>
				</table>
			</div>
		</div>
	);
};

export default ListFood;
