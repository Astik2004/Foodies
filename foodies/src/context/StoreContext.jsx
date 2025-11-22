import { createContext, useEffect, useState } from 'react'
import { fetchFoodList } from '../service/foodService'
import axios from 'axios'
import { addToCart, getCartData, removeQtyFromCart } from '../service/cartService'
export const StoreContext = createContext(null)

export const StoreContextProvider = props => {
  const [foodList, setFoodList] = useState([])
  const [quantities, setQuantities] = useState({})
  const [token, setToken] = useState('')
  const increaseQty = async foodId => {
    setQuantities(prev => ({ ...prev, [foodId]: (prev[foodId] || 0) + 1 }))
    await addToCart(foodId,token)
  }
  const decreaseQty = async foodId => {
    setQuantities(prev => ({
      ...prev,
      [foodId]: prev[foodId] > 0 ? prev[foodId] - 1 : 0
    }))
    await removeQtyFromCart(foodId,token)
  }
  const removeFromCart = foodId => {
    setQuantities(prevQuantities => {
      const updatedQuantities = { ...prevQuantities }
      delete updatedQuantities[foodId]
      return updatedQuantities
    })
  }
  const loadCart = async token => {
    const res=await getCartData(token)
    setQuantities(res)

  }
  const contextValue = {
    foodList,
    increaseQty,
    decreaseQty,
    quantities,
    setQuantities,
    removeFromCart,
    setToken,
    token,
    loadCart
  }

  useEffect(() => {
    async function fetchData () {
      const data = await fetchFoodList()
      setFoodList(data)
      if (localStorage.getItem('token')) {
        setToken(localStorage.getItem('token'))
        await loadCart(localStorage.getItem("token"))
      }
    }
    fetchData()
  }, [])

  return (
    <StoreContext.Provider value={contextValue}>
      {props.children}
    </StoreContext.Provider>
  )
}
