import React from "react";

const Menubar = ({ toggleSidebar }) => {
	return (
		<div>
			<nav className="navbar navbar-expand-lg border-bottom" style={{ background: 'var(--bg-surface)' }}>
				<div className="container-fluid py-2">
					<button className="btn btn-outline-primary shadow-sm" id="sidebarToggle" onClick={toggleSidebar}>
						<i className="bi bi-list fs-4"></i>
					</button>
                    <div className="d-flex align-items-center">
                        <span className="badge bg-primary rounded-pill px-3 py-2 fs-6 shadow-sm">Admin Dashboard</span>
                    </div>
				</div>
			</nav>
		</div>
	);
};

export default Menubar;
