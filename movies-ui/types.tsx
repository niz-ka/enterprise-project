export interface MovieResponse {
    model: 'MovieResponse';
    id: number;
    title: string;
    categoryName: string;
    year: number;
    description: string;
    price: number;
    image: string;
}

export interface CategoryResponse {
    model: 'CategoryResponse';
    id: number;
    name: string;
}

export interface ItemResponse {
    model: 'ItemResponse';
    movieId: number;
    title: string;
    price: number;
    image: string;
    quantity: number;
}

export interface CartResponse {
    model: 'CartResponse';
    id: string;
    total: number;
    items: ItemResponse[];
}

export interface ItemRequest {
    movieId: number;
    quantity: number;
}