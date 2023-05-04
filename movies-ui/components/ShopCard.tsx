import { ItemResponse } from '@/types'
import React from 'react'
import Image from 'next/image'
import {BsTrashFill} from 'react-icons/bs'

const ShopCard = ({ item, deleteItem }: { item: ItemResponse, deleteItem: Function }) => {
  return (
    <div className='shadow-lg p-2 rounded-lg bg-white flex items-center w-full gap-2'>
      <div className='w-36 h-22'>
        <Image src={item.image} alt={item.title} width={100} height={100} className='w-full h-full' />
      </div>
      <div className='w-full'>
        <div className='font-bold'>
          {item.title}
        </div>
        <button onClick={() => deleteItem(item)} className='flex gap-1 items-center content-center text-sm mt-3'>
          <BsTrashFill /> Delete
        </button>
      </div>
      <div className='text-right w-full'>
        <div>{item.quantity}</div>
        <div className='font-bold'>${(item.quantity * item.price).toFixed(2)}</div>
      </div>
    </div>
  )
}

export default ShopCard