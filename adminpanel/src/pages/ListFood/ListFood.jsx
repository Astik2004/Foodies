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
		<div className="pt-4 row justify-content-center">
			<div className="col-11 glass-panel p-4 border-0 mb-5">
                <h4 className="fw-bold mb-4">Food Inventory</h4>
				<div className="table-responsive">
					<table className="table table-hover align-middle">
						<thead>
							<tr>
								<th className="border-0 text-muted pb-3">Image</th>
								<th className="border-0 text-muted pb-3">Name</th>
								<th className="border-0 text-muted pb-3">Category</th>
								<th className="border-0 text-muted pb-3">Price</th>
								<th className="border-0 text-muted pb-3 text-end">Action</th>
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
								<td className="fw-semibold text-main">{food.name}</td>
								<td><span className="badge bg-light text-dark px-3 py-2 border">{food.category}</span></td>
								<td className="fw-bold text-gradient">&#8377;{food.price}.00</td>
								<td className="text-end">
									<i
										className="bi bi-trash3 text-danger"
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
		</div>
	);
};

export default ListFood;
