package com.ajzia.simplist.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.ajzia.simplist.firebase.FakeCategoryRepository
import com.ajzia.simplist.firebase.CategoryRepository
import com.ajzia.simplist.room.ProductDao
import com.ajzia.simplist.room.ProductListConverter
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
object TestAppModule {

  @Provides
  @Singleton
  fun providePreferencesDataStore(
    @ApplicationContext context: Context
  ): DataStore<Preferences> =
    PreferenceDataStoreFactory.create(
      produceFile = {
        context.preferencesDataStoreFile("test")
      }
    )

  @Provides
  @Singleton
  fun provideCategoryRepository(): CategoryRepository {
    return FakeCategoryRepository()
  }

  @Provides
  @Singleton
  fun provideProductListDatabase(
    @ApplicationContext context: Context
  ): ProductListDatabase {
    return Room.inMemoryDatabaseBuilder(
      context,
      ProductListDatabase::class.java
    ).addTypeConverter(ProductListConverter())
      .build()
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