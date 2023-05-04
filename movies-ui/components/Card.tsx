import { MovieResponse } from '@/types'
import Link from 'next/link'
import React from 'react'
import Image from 'next/image'

const Card = ({ movie }: { movie: MovieResponse }) => {
  return (
    <Link href={`/movies/${movie.id}`} className='h-full'>
      <div className='shadow-lg p-2 rounded-lg bg-white flex gap-4 h-full items-start'>
        <div className='w-20 shrink-0'>
          <Image src={movie.image} alt={movie.title} width={100} height={100} className='w-full h-full' />
        </div>
        <div>
          <div className='font-bold'>{movie.title}</div>
          <div>({movie.year})</div>
          <div>{movie.categoryName}</div>
          <div className='mt-2'>${movie.price.toFixed(2)}</div>
        </div>
      </div>
    </Link>
  )
}

export default Card