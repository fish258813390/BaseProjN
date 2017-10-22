package com.neil.fish.service.github;

import com.neil.fish.entity.Contributor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by neil on 2017/10/22 0022.
 */

public interface GithubService {

    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> repoContributors(@Path("owner") String owner, @Path("repo") String repo);
}
