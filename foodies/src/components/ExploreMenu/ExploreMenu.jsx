import React from 'react'
import { categories } from '../../assets/assets'
import './ExploreMenu.css'
import { useRef } from 'react'

function ExploreMenu({category,setCategory}) {
    const menuRef=useRef(null);

    const scrollLeft=()=>{
        if(menuRef.current)
        {
            menuRef.current.scrollBy({left:-300,behavior:'smooth'});
        }
    }
    const scrollRight=()=>{
        if(menuRef.current)
        {
            menuRef.current.scrollBy({left:300,behavior:'smooth'});
        }
    }
  return (
    <div className="explore-menu position-relative my-5 glass-panel p-5">
        <div className="d-flex align-items-center justify-content-between mb-4">
          <h2 className="display-6 fw-bold mb-0">Explore Our Menu</h2>
          <div className="d-flex gap-3">
            <button className="btn btn-glass rounded-circle p-2 d-flex align-items-center justify-content-center hover-scale" style={{width: '45px', height: '45px'}} onClick={scrollLeft}>
              <i className="bi bi-chevron-left fs-5"></i>
            </button>
            <button className="btn btn-glass rounded-circle p-2 d-flex align-items-center justify-content-center hover-scale" style={{width: '45px', height: '45px'}} onClick={scrollRight}>
              <i className="bi bi-chevron-right fs-5"></i>
            </button>
          </div>
        </div>
        <p className="fs-5 text-muted mb-5 col-md-8">Discover a variety of delicious dishes crafted with the freshest ingredients to satisfy your cravings.</p>
        
        <div className="d-flex gap-4 overflow-auto explore-menu-list py-3" ref={menuRef}>
          {
            categories.map((item, index) => {
                const isActive = item.category === category;
                return (
                    <div 
                      className={`text-center explore-menu-list-item hover-scale ${isActive ? 'active-item' : ''}`} 
                      key={index} 
                      onClick={()=>{setCategory(prev=>prev===item.category?'All':item.category)}}
                    >
                        <div className={`img-wrapper rounded-circle p-1 mb-3 mx-auto ${isActive ? 'gradient-border' : 'glass-border'}`}>
                          <img 
                            src={item.icon} 
                            alt={item.category} 
                            className="rounded-circle object-fit-cover w-100 h-100" 
                          />
                        </div>
                        <p className={`mt-2 fw-semibold fs-5 ${isActive ? 'text-primary' : 'text-light'}`}>{item.category}</p>
                    </div>
                )
            })}
        </div>
    </div>
  )
}

export default ExploreMenu
