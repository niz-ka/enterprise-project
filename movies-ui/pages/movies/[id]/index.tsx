import { ItemRequest, ItemResponse, MovieResponse } from '@/types'
import { GetServerSideProps } from 'next'
import React, { useEffect, useState } from 'react'
import Head from 'next/head'
import { changeItemInCart, getMovie } from '@/logic/api'
import { obtainCart } from '@/logic/cart'
import Image from 'next/image'

const MoviePage = ({ movie }: { movie: MovieResponse }) => {
    const title = `${movie.title} | Movies.db`
    const [item, setItem] = useState<ItemResponse | undefined>(undefined);
    const [cartId, setCartId] = useState<string | undefined>(undefined);

    useEffect(() => {
        obtainCart().then(cart => {
            const itemInCart = cart.items.find(item => item.movieId === movie.id)
            setItem(itemInCart)
            setCartId(cart.id)
        })
    }, [])

    return (
        <>
            <Head>
                <title>{title}</title>
            </Head>

            <div className='bg-white p-3 rounded-md shadow'>
                <div className='flex flex-col gap-1'>
                    <div>
                        {movie.categoryName}
                    </div>
                    <div className='text-xl font-bold '>
                        {movie.title}
                    </div>
                    <div>
                        ${movie.price.toFixed(2)}
                    </div>
                </div>
                <div className='pt-4'>
                    <Image src={movie.image} alt={movie.title} width={500} height={500} className='w-32 h-auto' priority={true} />
                </div>
                <div className='pt-5'>
                    {movie.description}
                </div>
                <div className='flex items-center gap-2'>
                    <button
                        onClick={async () => { const updatedItem = await handleAddToCart(cartId, item, movie); setItem(updatedItem) }}
                        className='bg-black inline-block text-white py-2 px-4 cursor-pointer rounded mt-5 hover:opacity-70 transition-opacity'>
                        Add to cart
                    </button>
                    <div className='border-black border py-2 px-4 mt-5 rounded'>
                        {item ? item.quantity : '0'}
                    </div>
                </div>
            </div>
        </>
    )
}

async function handleAddToCart(cartId: string | undefined, item: ItemResponse | undefined, movie: MovieResponse) {
    if (cartId === undefined) {
        return undefined;
    }
    const newQuantity = item ? item.quantity + 1 : 1
    const itemRequest: ItemRequest = { movieId: movie.id, quantity: newQuantity }
    const newCart = await changeItemInCart(cartId, itemRequest)
    return newCart.items.find(item => item.movieId === movie.id)
}

export const getServerSideProps: GetServerSideProps = async (context: any) => {
    const movie: MovieResponse = await getMovie(context.params.id)

    return {
        props: {
            movie
        }
    }
}

export default MoviePage