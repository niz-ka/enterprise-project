import { CartResponse } from "@/types";
import { createCart, getCart } from "./api";

export const obtainCart = async () => {
    const id = localStorage.getItem('cartId')
    if(id === null) {
        const cart = await createCart()
        localStorage.setItem('cartId', cart.id)
        return cart
    } 
    return getCart(id)
}

export const deleteCartFromStorage =  () => {
    localStorage.removeItem('cartId')
}