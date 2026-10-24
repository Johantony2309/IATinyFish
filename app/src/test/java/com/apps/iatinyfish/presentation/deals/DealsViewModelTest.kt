package com.apps.iatinyfish.presentation.deals

import com.apps.iatinyfish.data.model.ProductOffer
import com.apps.iatinyfish.data.repository.DealsRepository
import com.apps.iatinyfish.data.repository.PreviewDealsRepository
import com.apps.iatinyfish.data.repository.RemoteSearchException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DealsViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() { Dispatchers.setMain(dispatcher) }

    @After
    fun tearDown() { Dispatchers.resetMain() }

    @Test
    fun blankSearchShowsMessageWithoutCallingRepository() = runTest(dispatcher) {
        var calls = 0
        val repository = object : DealsRepository {
            override val isPreviewData = true
            override suspend fun search(query: String): List<ProductOffer> {
                calls++
                return emptyList()
            }
        }
        val viewModel = DealsViewModel(repository)
        assertFalse(viewModel.state.value.hasSearched)
        viewModel.onEvent(DealsEvent.SubmitSearch)
        runCurrent()
        assertEquals(0, calls)
        assertTrue(viewModel.state.value.hasSearched)
        assertFalse(viewModel.state.value.isLoading)
        assertEquals("Escribe un producto para iniciar la búsqueda.", viewModel.state.value.errorMessage)
    }

    @Test
    fun validSearchLoadsDemoResultsAndNavigatesToDetailAndBack() = runTest(dispatcher) {
        val response = CompletableDeferred<List<ProductOffer>>()
        val repository = object : DealsRepository {
            override val isPreviewData = true
            override suspend fun search(query: String) = response.await()
        }
        val viewModel = DealsViewModel(repository)
        viewModel.onEvent(DealsEvent.UseSuggestion("Nintendo Switch OLED"))
        runCurrent()
        assertTrue(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.hasSearched)
        val offers = PreviewDealsRepository.search("Nintendo Switch OLED")
        assertEquals(2, offers.size)
        response.complete(offers)
        runCurrent()
        assertFalse(viewModel.state.value.isLoading)
        assertEquals(offers, viewModel.state.value.offers)
        assertTrue(viewModel.state.value.isPreviewData)
        viewModel.onEvent(DealsEvent.OpenOffer(offers.first()))
        assertEquals(offers.first(), viewModel.state.value.selectedOffer)
        viewModel.onEvent(DealsEvent.Back)
        assertNull(viewModel.state.value.selectedOffer)
        assertEquals(offers, viewModel.state.value.offers)
    }

    @Test
    fun repositoryErrorStopsLoadingAndShowsMessage() = runTest(dispatcher) {
        val repository = object : DealsRepository {
            override val isPreviewData = true
            override suspend fun search(query: String): List<ProductOffer> {
                throw RemoteSearchException("Error de prueba")
            }
        }
        val viewModel = DealsViewModel(repository)
        viewModel.onEvent(DealsEvent.QueryChanged("Nintendo"))
        viewModel.onEvent(DealsEvent.SubmitSearch)
        runCurrent()
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.offers.isEmpty())
        assertEquals("Error de prueba", viewModel.state.value.errorMessage)
    }

    @Test
    fun unmatchedDemoQueryReturnsEmptyResults() = runTest(dispatcher) {
        val viewModel = DealsViewModel(PreviewDealsRepository)
        viewModel.onEvent(DealsEvent.UseSuggestion("zzzzzz"))
        runCurrent()
        assertTrue(viewModel.state.value.hasSearched)
        assertFalse(viewModel.state.value.isLoading)
        assertTrue(viewModel.state.value.offers.isEmpty())
        assertNull(viewModel.state.value.errorMessage)
    }
}
