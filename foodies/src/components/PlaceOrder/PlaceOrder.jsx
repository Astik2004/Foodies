import React, { useContext } from 'react';
import './PlaceOrder.css';
import { StoreContext } from '../../context/StoreContext';
import { calculateCartTotals } from '../../util/cartUtils';

function PlaceOrder() {
  const { foodList, quantities } = useContext(StoreContext);
  
  // Cart items
  const cartItems = foodList.filter((food) => quantities[food.id] > 0);
  const { total, tax, subTotal, shipping } = calculateCartTotals(cartItems, quantities);

  return (
    <div className="container mt-5 mb-5 min-vh-100">
      <main>
        <div className="py-4 text-center mb-4">
          <h2 className="display-5 fw-bold text-gradient">Secure Checkout</h2>
          <p className="text-muted lead">Complete your order with a few simple steps.</p>
        </div>

        <div className="row g-5">
          {/* Cart Section (Order Summary) */}
          <div className="col-md-5 col-lg-4 order-md-last">
            <div className="glass-panel p-4 sticky-top border-0 mb-4" style={{top: '20px'}}>
              <h4 className="d-flex justify-content-between align-items-center mb-4 pb-2 border-bottom border-secondary border-opacity-25">
                <span className="text-main fw-bold">Order Summary</span>
                <span className="badge bg-primary rounded-pill shadow-sm">{cartItems.length}</span>
              </h4>

              <div className="mb-4" style={{ maxHeight: '300px', overflowY: 'auto' }}>
                {cartItems.map(item => (
                  <div key={item.id} className="d-flex justify-content-between align-items-center mb-3">
                    <div>
                      <h6 className="my-0 text-main fw-semibold">{item.name}</h6>
                      <small className="text-muted">Qty: {quantities[item.id]}</small>
                    </div>
                    <span className="text-main fw-bold">&#8377;{(item.price * quantities[item.id]).toFixed(2)}</span>
                  </div>
                ))}
              </div>
              
              <hr className="border-secondary mb-3" />

              <div className="d-flex justify-content-between mb-2">
                <span className="text-muted">Subtotal</span>
                <span className="text-main">&#8377;{subTotal.toFixed(2)}</span>
              </div>
              <div className="d-flex justify-content-between mb-2">
                <span className="text-muted">Shipping</span>
                <span className="text-main">&#8377;{subTotal === 0 ? '0.00' : shipping.toFixed(2)}</span>
              </div>
              <div className="d-flex justify-content-between mb-4">
                <span className="text-muted">Tax (10%)</span>
                <span className="text-main">&#8377;{tax.toFixed(2)}</span>
              </div>

              <div className="d-flex justify-content-between align-items-center bg-glass p-3 rounded-3 border border-secondary border-opacity-25 mb-4">
                <span className="fw-bold fs-5 text-main">Total (INR)</span>
                <strong className="fs-4 text-gradient">&#8377;{total.toFixed(2)}</strong>
              </div>

              <form className="input-group mb-2">
                <input type="text" className="form-control bg-glass text-main border-secondary border-opacity-25" placeholder="Promo code" />
                <button type="button" className="btn btn-secondary px-4 fw-semibold border-secondary border-opacity-25">
                  Redeem
                </button>
              </form>
            </div>
          </div>

          {/* Billing Section */}
          <div className="col-md-7 col-lg-8">
            <div className="glass-panel p-4 p-md-5 border-0">
              <h4 className="mb-4 text-main fw-bold border-bottom border-secondary border-opacity-25 pb-3">Billing & Delivery Address</h4>
              
              <form className="needs-validation" noValidate>
                <div className="row g-4">
                  <div className="col-sm-6">
                    <label htmlFor="firstName" className="form-label text-muted">First name</label>
                    <input type="text" className="form-control bg-glass text-main border-secondary border-opacity-25" id="firstName" required placeholder="John" />
                  </div>

                  <div className="col-sm-6">
                    <label htmlFor="lastName" className="form-label text-muted">Last name</label>
                    <input type="text" className="form-control bg-glass text-main border-secondary border-opacity-25" id="lastName" required placeholder="Doe" />
                  </div>

                  <div className="col-12">
                    <label htmlFor="email" className="form-label text-muted">Email</label>
                    <input type="email" className="form-control bg-glass text-main border-secondary border-opacity-25" id="email" placeholder="you@example.com" />
                  </div>

                  <div className="col-12">
                    <label htmlFor="phone" className="form-label text-muted">Phone Number</label>
                    <input type="number" className="form-control bg-glass text-main border-secondary border-opacity-25" id="phone" placeholder="91XXXXXXXX" />
                  </div>

                  <div className="col-12">
                    <label htmlFor="address" className="form-label text-muted">Address</label>
                    <input type="text" className="form-control bg-glass text-main border-secondary border-opacity-25" id="address" placeholder="1234 Main St" required />
                  </div>

                  <div className="col-md-5">
                    <label htmlFor="country" className="form-label text-muted">Country</label>
                    <select className="form-select bg-glass text-main border-secondary border-opacity-25" id="country" required>
                      <option value="">Choose...</option>
                      <option>India</option>
                    </select>
                  </div>

                  <div className="col-md-4">
                    <label htmlFor="state" className="form-label text-muted">State</label>
                    <select className="form-select bg-glass text-main border-secondary border-opacity-25" id="state" required>
                      <option value="">Choose...</option>
                      <option>New Delhi</option>
                    </select>
                  </div>

                  <div className="col-md-3">
                    <label htmlFor="zip" className="form-label text-muted">Zip / Postal Code</label>
                    <input type="number" className="form-control bg-glass text-main border-secondary border-opacity-25" id="zip" placeholder="110052" required />
                  </div>
                </div>

                <hr className="my-5 border-secondary" />

                <button className="w-100 btn btn-gradient btn-lg py-3 fw-bold shadow hover-scale" type="submit" disabled={cartItems.length === 0}>
                  Confirm Order & Pay <i className="bi bi-shield-lock ms-2"></i>
                </button>
              </form>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}

export default PlaceOrder;
