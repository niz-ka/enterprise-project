import ShopCard from '@/components/ShopCard'
import { changeItemInCart, deleteCart } from '@/logic/api'
import { deleteCartFromStorage, obtainCart } from '@/logic/cart'
import { CartResponse, ItemRequest, ItemResponse } from '@/types'
import React, { useEffect, useState } from 'react'
import { BsTrashFill } from 'react-icons/bs'

const CartPage = () => {
    const [cart, setCart] = useState<CartResponse | undefined>(undefined)

    useEffect(() => { obtainCart().then(cart => setCart(cart)) }, [])

    async function deleteItem(item: ItemResponse) {
        if (!cart) {
            throw new Error('Cart undefined')
        }
        const cartId = cart.id
        const newQuantity = Math.max(item.quantity - 1, 0)
        const itemRequest: ItemRequest = { movieId: item.movieId, quantity: newQuantity }
        const updatedCart = await changeItemInCart(cartId, itemRequest)
        setCart(updatedCart)
    }

    async function deleteAll() {
        if (!cart) {
            throw new Error('Cart undefined')
        }
        deleteCart(cart.id)
        deleteCartFromStorage()
        const newCart = await obtainCart()
        setCart(newCart)
    }

    return (
        <div>
            <div className='flex flex-col gap-5'>
                {cart?.items.map(item => <ShopCard key={item.movieId} item={item} deleteItem={deleteItem} />)}
            </div>
            <div className='shadow-lg px-2 rounded-lg bg-white mt-12 font-bold text-xl py-5'>
                <div className='flex items-center justify-between'>
                    <div>Total</div>
                    <div>${cart?.total.toFixed(2)}</div>
                </div>
                <button onClick={() => deleteAll()} className='flex bg-red-600 text-white items-center p-2 rounded-lg text-base mt-2'>
                    <BsTrashFill /> Delete cart
                </button>
            </div>
        </div>
    )
}

export default CartPage