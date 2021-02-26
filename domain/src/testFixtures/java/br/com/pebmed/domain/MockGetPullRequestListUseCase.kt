package br.com.pebmed.domain

import br.com.pebmed.domain.base.ResultWrapper
import br.com.pebmed.domain.usecases.GetPullRequestListUseCase
/**
 * https://issuetracker.google.com/issues/139438142
 * Bug na IDE que não encontra dependências importadas
 * O build continua funcionando normalmente mesmo com esse problema
 * gerado ao utilizar o java-test-fixtures plugin
 */
import io.mockk.*

class MockGetPullRequestListUseCase(val mock: GetPullRequestListUseCase) {

    fun mockSuccess() {
        coEvery {
            mock.runAsync(any())
        } returns ResultWrapper(success = MockPullRequestModel.mockListWithOneGenericItem())
    }

    fun mockSuccessWithEmptyList() {
        coEvery {
            mock.runAsync(any())
        } returns ResultWrapper(success = MockPullRequestModel.mockEmptyList())
    }

    fun mockError() {
        coEvery {
            mock.runAsync(any())
        } returns ResultWrapper(error = MockBaseErrorData.mockStatusError())
    }

    companion object {
        fun mockGenericParams() = GetPullRequestListUseCase.Params(
            owner = "OwnerModel",
            repoName = "RepoName"
        )
    }
}