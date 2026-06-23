package com.ajzia.simplist.di

import android.content.Context
import com.ajzia.simplist.firebase.CategoryRepository
import com.ajzia.simplist.firebase.FirestoreCategoryRepository
import com.ajzia.simplist.room.ProductDao
import com.ajzia.simplist.room.ProductListDao
import com.ajzia.simplist.room.ProductListDatabase
import com.ajzia.simplist.room.ProductListRepository
import com.ajzia.simplist.room.ProductRepository
import com.google.firebase.firestore.FirebaseFirestore
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
  fun provideFirebaseFirestore(): FirebaseFirestore {
    return FirebaseFirestore.getInstance()
  }

  @Provides
  @Singleton
  fun provideCategoryRepository(
    repository: FirestoreCategoryRepository
  ): CategoryRepository {
    return repository
  }

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
  fun provideProductDao(
    productListDatabase: ProductListDatabase
  ): ProductDao {
    return productListDatabase.productDao()
  }

  @Provides
  fun provideProductListRepository(
    productListDao: ProductListDao
  ): ProductListRepository {
    return ProductListRepository(productListDao)
  }

  @Provides
  fun provideProductRepository(
    productDao: ProductDao
  ): ProductRepository {
    return ProductRepository(productDao)
  }

}