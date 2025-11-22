import axios from "axios";

const API_URL = "http://localhost:8080/api/foods";

export const fetchFoodList=async()=>{
    try{
        const res=await axios.get(API_URL)
        console.log(res.data);
        return res.data;
    }catch(error){
        console.error("Error fetching food list:", error);
        throw error;
    }
};

export const fetchFoodDetails=async(id)=>{
    try {
        const res=await axios.get(API_URL+"/"+id)
        return res.data
    } catch (error) {
        console.log("Error fetching Details")
        throw error
    }

}

