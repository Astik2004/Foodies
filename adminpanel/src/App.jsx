import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Sidebar from "./component/Sidebar/Sidebar";
import Menubar from "./component/Menubar/Menubar";
import AddFood from "./pages/AddFood/AddFood";
import ListFood from "./pages/ListFood/ListFood";
import Orders from "./pages/Orders/Orders";
import { useState } from "react";
import { ToastContainer } from "react-toastify";

function App() {
	const [sideBarVisible, setSideBarVisible] = useState(true);
	const toggleSidebar = () => {
		setSideBarVisible(!sideBarVisible);
	};
	return (
		<div className="d-flex" id="wrapper">
			<Sidebar sideBarVisible={sideBarVisible} />
			<div id="page-content-wrapper">
				<Menubar toggleSidebar={toggleSidebar} />
				<ToastContainer />
				<div className="container-fluid">
					<Routes>
						<Route path="/add" element={<AddFood />} />
						<Route path="/list" element={<ListFood />} />
						<Route path="/orders" element={<Orders />} />
						<Route path="/" element={<ListFood />} />
					</Routes>
				</div>
			</div>
		</div>
	);
}

export default App;
