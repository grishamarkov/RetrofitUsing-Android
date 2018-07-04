package com.n2me.androidtv.common.rest;

import android.util.Log;

import com.n2me.androidtv.common.bus.BusProvider;
import com.n2me.androidtv.common.event.CategoriesEvent;
import com.n2me.androidtv.common.event.MediaEvent;
import com.n2me.androidtv.common.event.ServiceErrorEvent;
import com.n2me.androidtv.common.event.UserLoginFailedEvent;
import com.n2me.androidtv.common.event.UserLoginSuccessEvent;
import com.n2me.androidtv.common.event.UsersEvent;
import com.n2me.androidtv.common.rest.model.Categories;
import com.n2me.androidtv.common.rest.model.Media;
import com.n2me.androidtv.common.rest.model.User;
import com.n2me.androidtv.common.rest.model.Users;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class N2meWebService {
    public static String TAG = N2meWebService.class.getSimpleName();

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static boolean isDisabled = false;

    private Retrofit client;
    private N2meApi mN2meApi;

    private static N2meWebService instance = null;
    public static N2meWebService getInstance() {
        if (instance == null)
            instance = new N2meWebService();

        return instance;
    }

    public N2meWebService()
    {
        if (isDisabled == true)
            return;

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Request original = chain.request();
                        okhttp3.Request request = original.newBuilder()
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                })
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        client = new Retrofit.Builder()
                .baseUrl(Api.SERVER)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mN2meApi = client.create(N2meApi.class);

        BusProvider.get().register(this);

    }

    public synchronized void session(final String email, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<Users> call = mN2meApi.session(email, password);
                call.enqueue(new Callback<Users>() {
                    @Override
                    public void onResponse(Call<Users> call, Response<Users> response) {
                        Log.v(TAG, "Call to /session is succeeded");

                        if (response != null) {
                            Users user = response.body();
                            if (user != null) {
                                UserLoginSuccessEvent event = new UserLoginSuccessEvent(user);
                                BusProvider.get().post(event);
                            }
                            else {
                                UserLoginFailedEvent failedEvent = new UserLoginFailedEvent();
                                BusProvider.get().post(failedEvent);
                            }
                        }
                        else {
                            UserLoginFailedEvent failedEvent = new UserLoginFailedEvent();
                            BusProvider.get().post(failedEvent);
                        }
                    }


                    @Override
                    public void onFailure(Call<Users> call, Throwable t) {
                        Log.v(TAG, "Call to /session is failed");
                    }
                });
            }
        }).start();
    }

    public synchronized void user(final String userEmail, final String userToken) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<Users> call = mN2meApi.user(userEmail, userToken);
                call.enqueue(new Callback<Users>() {
                    @Override
                    public void onResponse(Call<Users> call, Response<Users> response) {
                        Log.v(TAG, "Call to /user/ is succeeded.");

                        if (response != null) {
                            if (response.body() != null) {
                                Users users = response.body();
                                UsersEvent event = new UsersEvent(users);
                                BusProvider.get().post(event);
                            } else {
                                ServiceErrorEvent event = new ServiceErrorEvent("Couldn't get the users information");
                                BusProvider.get().post(event);
                            }
                        }
                        else {
                            ServiceErrorEvent event = new ServiceErrorEvent("Couldn't get the users information");
                            BusProvider.get().post(event);
                        }
                    }

                    @Override
                    public void onFailure(Call<Users> call, Throwable t) {
                        Log.v(TAG, "Call to /user/ is failed.");

                        ServiceErrorEvent event = new ServiceErrorEvent("Couldn't call the users API");
                        BusProvider.get().post(event);
                    }
                });
            }
        }).start();
    }

    public synchronized void categories() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Call<Categories> call = mN2meApi.categoriesAll();
                call.enqueue(new Callback<Categories>() {
                    @Override
                    public void onResponse(Call<Categories> call, Response<Categories> response) {
                        Log.v(TAG, "Call to /categories/ is succeeded.");

                        if (response != null) {
                            if (response.body() != null) {
                                Categories categories = response.body();
                                CategoriesEvent event = new CategoriesEvent(CategoriesEvent.TYPE_CATEGORY_ALL, categories);
                                BusProvider.get().post(event);
                            } else {
                                ServiceErrorEvent event = new ServiceErrorEvent("Couldn't get the categories");
                                BusProvider.get().post(event);
                            }
                        }
                        else {
                            ServiceErrorEvent event = new ServiceErrorEvent("Couldn't get the categories");
                            BusProvider.get().post(event);
                        }
                    }

                    @Override
                    public void onFailure(Call<Categories> call, Throwable t) {
                        Log.v(TAG, "Call to /categories/ is failed.");

                        ServiceErrorEvent event = new ServiceErrorEvent("Couldn't call the categories API");
                        BusProvider.get().post(event);
                    }
                });
            }
        }).start();
    }

    public synchronized void categories(final String userEmail, final String userToken) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<Categories> call = mN2meApi.categories(userEmail, userToken);
                call.enqueue(new Callback<Categories>() {
                    @Override
                    public void onResponse(Call<Categories> call, Response<Categories> response) {
                        Log.v(TAG, "Call to /categories/ with email/token is succeeded.");

                        if (response != null) {
                            if (response.body() != null) {
                                Categories categories = response.body();
                                CategoriesEvent event = new CategoriesEvent(CategoriesEvent.TYPE_CATEGORY_WITH_EMAIL, categories);
                                BusProvider.get().post(event);
                            } else {
                                ServiceErrorEvent event = new ServiceErrorEvent("Couldn't get the categories (with mail)");
                                BusProvider.get().post(event);
                            }
                        }
                        else {
                            ServiceErrorEvent event = new ServiceErrorEvent("Couldn't get the categories (with mail)");
                            BusProvider.get().post(event);
                        }
                    }

                    @Override
                    public void onFailure(Call<Categories> call, Throwable t) {
                        Log.v(TAG, "Call to /categories/ with email/token is failed.");

                        ServiceErrorEvent event = new ServiceErrorEvent("Couldn't call the categories API (with mail)");
                        BusProvider.get().post(event);
                    }
                });
            }
        }).start();
    }

    public synchronized void categories(final int categoryId, final String userEmail, final String userToken) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<Categories> call = mN2meApi.categoriesById(categoryId, userEmail, userToken);
                call.enqueue(new Callback<Categories>() {
                    @Override
                    public void onResponse(Call<Categories> call, Response<Categories> response) {
                        Log.v(TAG, "Call to /categories/ with categoryid is succeeded");

                        if (response != null) {
                            if (response.body() != null) {
                                Categories categories = response.body();
                                CategoriesEvent event = new CategoriesEvent(CategoriesEvent.TYPE_CATEGORY_WITH_ID, categories);
                                BusProvider.get().post(event);
                            } else {
                                ServiceErrorEvent event = new ServiceErrorEvent("Couldn't get the categories (with id)");
                                BusProvider.get().post(event);
                            }
                        }
                        else {
                            ServiceErrorEvent event = new ServiceErrorEvent("Couldn't get the categories (with id)");
                            BusProvider.get().post(event);
                        }
                    }

                    @Override
                    public void onFailure(Call<Categories> call, Throwable t) {
                        Log.v(TAG, "Call to /categories/ with categoryId is failed.");

                        ServiceErrorEvent event = new ServiceErrorEvent("Couldn't call the categories API (with id)");
                        BusProvider.get().post(event);
                    }
                });
            }
        }).start();
    }

    public synchronized void media(final int categoryId, final int mediaId, final String userEmail, final String userToken) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<Media> call = mN2meApi.media(categoryId, mediaId, userEmail, userToken);
                call.enqueue(new Callback<Media>() {
                    @Override
                    public void onResponse(Call<Media> call, Response<Media> response) {
                        Log.v(TAG, "Call to /media/ with categoryid & mediaid is succeeded.");

                        if (response != null) {
                            if (response.body() != null) {
                                Media media = response.body();
                                MediaEvent event = new MediaEvent(MediaEvent.TYPE_MEDIA, media);
                                BusProvider.get().post(event);
                            } else {
                                ServiceErrorEvent event = new ServiceErrorEvent("Couldn't get the media list (with category id)");
                                BusProvider.get().post(event);
                            }
                        }
                        else {
                            ServiceErrorEvent event = new ServiceErrorEvent("Couldn't get the media list (with category id)");
                            BusProvider.get().post(event);
                        }
                    }

                    @Override
                    public void onFailure(Call<Media> call, Throwable t) {
                        Log.v(TAG, "Call to /media/ with categoryId & mediaId is failed.");

                        ServiceErrorEvent event = new ServiceErrorEvent("Couldn't call the media API (with category id)");
                        BusProvider.get().post(event);
                    }
                });
            }
        }).start();
    }

    public synchronized void mediaByMediaId(final int mediaId, final String userEmail, final String userToken) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<Media> call = mN2meApi.mediaById(mediaId, userEmail, userToken);
                call.enqueue(new Callback<Media>() {
                    @Override
                    public void onResponse(Call<Media> call, Response<Media> response) {
                        Log.v(TAG, "Call to /media/ with mediaId is succeeded.");

                        if (response != null) {
                            if (response.body() != null) {
                                Media media = response.body();
                                MediaEvent event = new MediaEvent(MediaEvent.TYPE_MEDIA_ID, media);
                                BusProvider.get().post(event);
                            } else {
                                ServiceErrorEvent event = new ServiceErrorEvent("Couldn't get the media list (with media id)");
                                BusProvider.get().post(event);
                            }
                        }
                        else {
                            ServiceErrorEvent event = new ServiceErrorEvent("Couldn't get the media list (with media id)");
                            BusProvider.get().post(event);
                        }
                    }

                    @Override
                    public void onFailure(Call<Media> call, Throwable t) {
                        Log.v(TAG, "Call to /media/ with mediaId is failed.");

                        ServiceErrorEvent event = new ServiceErrorEvent("Couldn't call the media API (with media id)");
                        BusProvider.get().post(event);
                    }
                });
            }
        }).start();
    }

    public synchronized void mediaByCategoryId(final int categoryId, final String userEmail, final String userToken) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Call<Media> call = mN2meApi.mediaByCategoryId(categoryId, 1, 10,userEmail, userToken);
                Log.v(TAG, "Request: /media/ with categoryId (" + categoryId + ")");

                call.enqueue(new Callback<Media>() {
                    @Override
                    public void onResponse(Call<Media> call, Response<Media> response) {
                        Log.v(TAG, "Response: /media/ with categoryId (" + categoryId + ")");

                        if (response != null) {
                            if (response.body() != null) {
                                Media media = response.body();
                                MediaEvent event = new MediaEvent(MediaEvent.TYPE_MEDIA_BY_CATEGORYID, categoryId, media);
                                BusProvider.get().post(event);
                            } else {
                                ServiceErrorEvent event = new ServiceErrorEvent("Couldn't get the media list (with category id)");
                                BusProvider.get().post(event);
                            }
                        }
                        else {
                            ServiceErrorEvent event = new ServiceErrorEvent("Couldn't get the media list (with category id)");
                            BusProvider.get().post(event);
                        }
                    }

                    @Override
                    public void onFailure(Call<Media> call, Throwable t) {
                        Log.v(TAG, "Failed: /media/ with categoryId (" + categoryId + ")");
                        t.printStackTrace();

                        ServiceErrorEvent event = new ServiceErrorEvent("Couldn't call the media API (with category id)");
                        BusProvider.get().post(event);
                    }
                });
            }
        }).start();
    }

}
