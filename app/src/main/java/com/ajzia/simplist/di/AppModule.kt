package com.ajzia.simplist.di

import android.content.Context
import com.ajzia.simplist.room.ProductListDao
import com.ajzia.simplist.room.ProductListDatabase
import com.ajzia.simplist.room.ProductListRepository
import com.ajzia.simplist.room.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  @Singleton
  fun provideProductListDatabase(
    @ApplicationContext context: Context
  ): ProductListDatabase {
    return ProductListDatabase.getDatabase(context)
  }

  @Provides
  fun provideProductListDao(
    productListDatabase: ProductListDatabase
  ): ProductListDao {
    return productListDatabase.productListDao()
  }

  @Provides
  fun provideProductListRepository(
    productListDao: ProductListDao
  ): ProductListRepository {
    return ProductListRepository(productListDao)
  }

  @Provides
  fun provideProductRepository(
    productListDao: ProductListDao
  ): ProductRepository {
    return ProductRepository(productListDao)
  }

}