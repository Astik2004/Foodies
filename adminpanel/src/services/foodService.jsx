import axios from "axios";
const API_URL = "http://localhost:8080/api/foods/";

const addFood = async (foodData, image) => {
  const formData = new FormData();
  formData.append("food", JSON.stringify(foodData));
  formData.append("file", image);

  try {
    await axios.post(API_URL+"add-food", formData, {headers: {"Content-Type": "multipart/form-data",}});
  } catch (error) {
    console.error("Error adding food:", error);
    throw error;
  }
};

const listFood = async () => {
  try {
    const response = await axios.get(API_URL + "get-all-foods");
    return response.data;
  } catch (error) {
    console.error("Error fetching food list:", error);
    throw error;
  }
};

const deleteFood = async (id) => {
  try {
    const confirmDelete = confirm("Are you sure you want to delete this food item?");
    if (!confirmDelete) {
      return false; 
    }
    const response = await axios.delete(`${API_URL}delete/${id}`);
    return response.status === 200;
  } catch (error) {
    console.error("Error deleting food:", error);
    throw error;
  }
};


export { addFood, listFood, deleteFood };
