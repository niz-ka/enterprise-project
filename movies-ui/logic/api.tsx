import { CartResponse, CategoryResponse, ItemRequest, MovieResponse } from "@/types"
import getConfig from 'next/config'

const { publicRuntimeConfig } = getConfig()

export const getAllMovies = async() => {
    const response: Response = await fetch(`${publicRuntimeConfig.apiUrl}/movies`)
    const movies: MovieResponse[] = await response.json()
    return movies
}

export const getMovie = async(id: number) => {
    const response: Response = await fetch(`${publicRuntimeConfig.apiUrl}/movies/${id}`)
    const movie: MovieResponse = await response.json()
    return movie
}

export const getAllCategories = async() => {
    const response: Response = await fetch(`${publicRuntimeConfig.apiUrl}/categories`)
    const categories: CategoryResponse[] = await response.json()
    return categories
}

export const createCart = async() => {
    const response: Response = await fetch(`${publicRuntimeConfig.apiUrl}/carts`, {
        method: 'POST'
    })
    const cart: CartResponse = await response.json()
    return cart
}

export const getCart = async(id: string) => {
    const response: Response = await fetch(`${publicRuntimeConfig.apiUrl}/carts/${id}`)
    const cart: CartResponse = await response.json()
    return cart
}

export const changeItemInCart = async(id: string, itemRequest: ItemRequest) => {
    const response: Response = await fetch(`${publicRuntimeConfig.apiUrl}/carts/${id}`, {
        method: 'PATCH',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(itemRequest)
    })
    const cart: CartResponse = await response.json()
    return cart
}

export const deleteCart = async(id: string) => {
    fetch(`${publicRuntimeConfig.apiUrl}/carts/${id}`, {
        method: 'DELETE'
    })
}

