import React from 'react'
import Link from 'next/link'
import { RiShoppingCart2Fill } from 'react-icons/ri'

const Layout = ({ children }: { children: any }) => {
  return (
    <>
      <header className='bg-white py-3 px-7 text-2xl font-bold shadow-md flex items-center justify-between'>
        <Link href="/">Movies.db</Link>
        <Link href="/cart">
          <div className='border-2 border-black rounded-full p-2 bg-white cursor-pointer hover:opacity-75 transition-opacity '>
            <RiShoppingCart2Fill />
          </div>
        </Link>
      </header>

      <main>
        <div className='max-w-5xl m-auto mt-10 p-4'>
          {children}
        </div>
      </main>

      <footer className='bg-white py-3 px-7 border-t-2'>
        © {new Date().getFullYear()} Movies.db
      </footer>
    </>
  )
}

export default Layout