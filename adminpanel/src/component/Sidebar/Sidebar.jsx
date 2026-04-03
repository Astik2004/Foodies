import React from "react";
import { Link } from "react-router-dom";
import { assets } from "../../assets/assets";

const Sidebar = ({ sideBarVisible }) => {
	return (
		<div
			className={`border-end ${sideBarVisible ? "d-block" : "d-none"}`}
			id="sidebar-wrapper"
            style={{ background: 'var(--bg-surface)' }}
		>
			<div className="sidebar-heading border-bottom py-3 d-flex align-items-center justify-content-center">
				<img src={assets.logo} alt="logo" height={45} className="hover-scale" />
			</div>
			<div className="list-group list-group-flush mt-3">
				<Link
					className="list-group-item list-group-item-action border-0 p-3 mb-1 rounded-3 hover-lift text-main fw-semibold"
					to="/list"
				>
					<i className="bi bi-list-ul me-2"></i>
					List Food
				</Link>
				<Link
					className="list-group-item list-group-item-action border-0 p-3 mb-1 rounded-3 hover-lift text-main fw-semibold"
					to="/add"
				>
					<i className="bi bi-plus-circle me-2"></i>
					Add Food
				</Link>
				<Link
					className="list-group-item list-group-item-action border-0 p-3 mb-1 rounded-3 hover-lift text-main fw-semibold"
					to="/orders"
				>
					<i className="bi bi-bag-check me-2"></i>
					Orders
				</Link>
			</div>
		</div>
	);
};

export default Sidebar;
