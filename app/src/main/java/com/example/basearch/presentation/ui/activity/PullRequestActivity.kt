package com.example.basearch.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import br.com.pebmed.domain.entities.PullRequest
import com.bumptech.glide.Glide
import com.example.basearch.R
import com.example.basearch.presentation.extensions.setGone
import com.example.basearch.presentation.extensions.setVisible
import com.example.basearch.presentation.ui.ViewStateResource
import com.example.basearch.presentation.ui.viewmodel.PullRequestViewModel
import kotlinx.android.synthetic.main.activity_pull_request.*
import kotlinx.android.synthetic.main.item_pull_request_list.view.*
import kotlinx.android.synthetic.main.item_repo_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class PullRequestActivity : AppCompatActivity() {

    private val viewModel by viewModel<PullRequestViewModel>()

    private lateinit var owner: String
    private lateinit var repoName: String
    private var pullRequestId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_request)

        this.initObservers()

        owner = intent.extras?.getString("owner").toString()
        repoName = intent.extras?.getString("repoName").toString()
        pullRequestId = intent.extras?.getLong("pullRequestId")!!


        viewModel.getPullRequest(owner,  repoName, pullRequestId.toInt())
    }

    private fun initObservers() {
        viewModel.pullRequestState.observe(this, Observer {
            when (it) {
                is ViewStateResource.Loading -> {
                    showLoadingView()
                }

                is ViewStateResource.Success -> {
                    showContent(it.data)
                }

                is ViewStateResource.Empty -> {
                    val message = getString(R.string.empty_list)
                }

                is ViewStateResource.Error -> {
                    Log.e("", "Show error")
                }
            }
        })
    }

    //region ViewStates
    private fun showLoadingView() {
        hideErrorView()
        hideContent()

        layoutLoading.setVisible()
    }

    private fun hideLoadingView() {
        layoutLoading.setGone()
    }
    private fun showErrorView(message: String) {
        hideLoadingView()
        hideContent()

        textReposError.text = message
        layoutError.setVisible()
    }

    private fun hideErrorView() {
        layoutError.setGone()
        textReposError.text = ""
    }

    private fun showContent(pullRequest: PullRequest) {
        hideLoadingView()
        hideErrorView()

        textAuthorName.text = pullRequest.user.login
        textTitle.text = pullRequest.title
        textDescription.text = pullRequest.body
        textComments.text = pullRequest.comments.toString()
        textCommits.text = pullRequest.commits.toString()
        textAdditions.text = pullRequest.additions.toString()
        textDeletions.text = pullRequest.deletions.toString()
        textChangedFiles.text = pullRequest.changedFiles.toString()


        textDate.text = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(pullRequest.createdAt)

        Glide.with(this).load(pullRequest.user.avatarUrl)
            .placeholder(R.drawable.ic_person)
            .error(R.drawable.ic_person)
            .into(imagePullRequestAuthor)

        layoutContent.setVisible()
    }

    private fun hideContent() {
        layoutContent.setGone()
    }
    //endregion
}
