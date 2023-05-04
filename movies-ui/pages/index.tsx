import { GetServerSideProps } from 'next'
import { CategoryResponse, MovieResponse } from '@/types'
import Card from '@/components/Card'
import Head from 'next/head'
import { getAllCategories, getAllMovies } from '@/logic/api'
import { ChangeEvent, useState } from 'react'

const Home = ({ movies, categories }: { movies: MovieResponse[], categories: CategoryResponse[] }) => {
  const [category, setCategory] = useState<string>('All')
  
  return (
    <>
      <Head>
        <title>Home | Movies.db</title>
      </Head>
      <div className='mb-8'>
        <select value={category} onChange={(e) => setCategory(e.target.value)} className='cursor-pointer rounded-lg p-2 shadow-lg font-bold'>
          <option>All</option>
          {categories.map(category => <option key={category.id}>{category.name}</option>)}
        </select>
      </div>
      <div className='grid grid-cols-1 md:grid-cols-3 gap-4'>
        {movies.filter(movie => category === 'All' || movie.categoryName === category).map(movie => <Card movie={movie} key={movie.id} />)}
      </div>
    </>
  )
}

export const getServerSideProps: GetServerSideProps = async () => {
  const movies: MovieResponse[] = await getAllMovies()
  const categories: CategoryResponse[] = await getAllCategories()

  return {
    props: {
      movies,
      categories
    }
  }
}

export default Home
