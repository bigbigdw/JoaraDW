package bigbigdw.joaradw.book

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import bigbigdw.joaradw.etc.HELPER
import bigbigdw.joaradw.util.LoginResult
import bigbigdw.joaradw.util.LoginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBookList {
    fun getUserHistoryBooks(token: String?, page: String?, offset: String?): Call<BookListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainHistoryBookService::class.java)
            .getRetrofit(
                token,
                page,
                offset,
                ""
            )
    }

    fun getRecommendBooks(token: String?, page: String?, offset: String?): Call<BookListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainRecommendBookService::class.java)
            .getRetrofit(
                token,
                page,
                offset,
                ""
            )
    }

    fun getMDBooks(token: String?, page: String?, offset: String?): Call<BookListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainMDBookService::class.java)
            .getRetrofit(
                token,
                page,
                offset,
                "recommend_book"
            )
    }

    fun getMDWebtoon(token: String?): Call<BookListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainMDWebtoonService::class.java)
            .getRetrofit(
                token,
                "",
                "",
                ""
            )
    }

    fun getMDEventList(token: String?): Call<BookEventListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainBookEventListService::class.java)
            .getRetrofit(
                token
            )
    }

    fun getBookListB(token: String?, store: String?, orderby: String?, showType: String?): Call<BookListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainBookListBService::class.java)
            .getRetrofit(
                token,
                store,
                orderby,
                showType,
            )
    }

    fun getBookListC(token: String?, sectionMode: String?, showType: String?, notyYear: String?): Call<BookListResultC?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainBookListCService::class.java)
            .getRetrofit(
                token,
                sectionMode,
                showType,
                notyYear
            )
    }

    fun getBookListD(token: String?, sectionMode: String?, store: String?, orderby: String?, showType: String?, category: String?): Call<BookListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MainBookListDService::class.java)
            .getRetrofit(
                token,
                sectionMode,
                store,
                orderby,
                showType,
                category
            )
    }

    fun getNewBook(token: String?, store: String?, page : Int? , category: String?): Call<BookListResultC?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(NewBookService::class.java)
            .getRetrofit(
                token,
                store,
                page,
                category
            )
    }

    fun postFav(bookCode: String?, context: Context?, bookTitle : String?) {
        val token = context!!.getSharedPreferences("LOGIN", AppCompatActivity.MODE_PRIVATE)
            .getString("TOKEN", "")

        val call = Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(FavBookService::class.java)
            .postRetrofit(
                token,
                bookCode,
                "1",
                HELPER.API_KEY,
                HELPER.VER,
                HELPER.DEVICE,
                HELPER.DEVICE_ID,
                HELPER.DEVICE_TOKEN
            )!!

            call.enqueue(object : Callback<BookFavResult?> {
            override fun onResponse(
                call: Call<BookFavResult?>,
                response: retrofit2.Response<BookFavResult?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        val status = it.status
                        val favorite = it.favorite

                        if(status.equals("1")){
                            if(favorite!!){

                                Toast.makeText(
                                    context, "'$bookTitle'이(가) 선호작에 등록되었습니다",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context, "'$bookTitle'을(를) 선호작에서 해제하였습니다",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<BookFavResult?>, t: Throwable) {
                Toast.makeText(context, "선호작 등록에 실패하였습니다", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getBookBest(token: String?, best: String?, store: String?, category: String?): Call<BookListBestResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(BookListBestService::class.java)
            .getRetrofit(
                token,
                best,
                store,
                category
            )
    }

    fun getBookBestA(token: String?, best: String?, store: String?, category: String?): Call<BookListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(BookListBestServiceA::class.java)
            .getRetrofit(
                token,
                best,
                store,
                category
            )
    }

    fun getBookFinish(token: String?, store: String?, orderby: String?, category: String?): Call<BookListResultC?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(BookListFinishService::class.java)
            .getRetrofit(
                token,
                store,
                orderby,
                category
            )
    }

    fun getNewBookD(token: String?, store: String?, page : Int? , category: String?): Call<BookListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(NewBookDService::class.java)
            .getRetrofit(
                token,
                store,
                page,
                category
            )
    }

    fun getBookFinishD(token: String?, store: String?, page : Int? , category: String?): Call<BookListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(BookListFinishDService::class.java)
            .getRetrofit(
                token,
                store,
                category
            )
    }

    fun getBookFav(token: String?, orderby: String?, page : Int? ): Call<BookListResultC?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(BookListFavService::class.java)
            .getRetrofit(
                token,
                orderby,
                page
            )
    }

    fun getBookHistory(token: String?, page : Int? ): Call<BookListResult?>? {
        return Retrofit.Builder()
            .baseUrl(HELPER.API)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(BookListHistoryService::class.java)
            .getRetrofit(
                token,
                page
            )
    }
}