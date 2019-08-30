package br.com.pebmed.data.remote.model.response

import br.com.pebmed.data.remote.model.RepoPayload
import com.google.gson.annotations.SerializedName

data class GetReposResponse(
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val repoPayloads: List<RepoPayload>,
    @SerializedName("total_count") val totalCount: Int
)