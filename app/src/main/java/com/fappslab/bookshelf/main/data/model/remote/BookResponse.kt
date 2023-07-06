package com.fappslab.bookshelf.main.data.model.remote

import com.google.gson.annotations.SerializedName

data class BookResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("etag")
    val etag: String?,
    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfoResponse?,
    @SerializedName("saleInfo")
    val saleInfo: SaleInfoResponse?,
    @SerializedName("accessInfo")
    val accessInfo: AccessInfoResponse?,
    @SerializedName("searchInfo")
    val searchInfo: SearchInfoResponse?
) {
    data class VolumeInfoResponse(
        @SerializedName("title")
        val title: String?,
        @SerializedName("subtitle")
        val subtitle: String?,
        @SerializedName("authors")
        val authors: List<String>?,
        @SerializedName("publisher")
        val publisher: String?,
        @SerializedName("publishedDate")
        val publishedDate: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("pageCount")
        val pageCount: Int?,
        @SerializedName("printType")
        val printType: String?,
        @SerializedName("categories")
        val categories: List<String>?,
        @SerializedName("contentVersion")
        val contentVersion: String?,
        @SerializedName("imageLinks")
        val imageLinks: ImageLinksResponse?,
        @SerializedName("language")
        val language: String?,
        @SerializedName("previewLink")
        val previewLink: String?,
        @SerializedName("infoLink")
        val infoLink: String?,
        @SerializedName("canonicalVolumeLink")
        val canonicalVolumeLink: String?
    ) {
        data class ImageLinksResponse(
            @SerializedName("smallThumbnail")
            val smallThumbnail: String?,
            @SerializedName("thumbnail")
            val thumbnail: String?
        )
    }

    data class SaleInfoResponse(
        @SerializedName("country")
        val country: String?,
        @SerializedName("saleability")
        val saleability: String?,
        @SerializedName("isEbook")
        val isEbook: Boolean?,
        @SerializedName("listPrice")
        val listPrice: SalePriceResponse?,
        @SerializedName("retailPrice")
        val retailPrice: SalePriceResponse?,
        @SerializedName("buyLink")
        val buyLink: String?,
        @SerializedName("offers")
        val offers: List<OfferResponse>?
    ) {
        data class SalePriceResponse(
            @SerializedName("amount")
            val amount: Double?,
            @SerializedName("currencyCode")
            val currencyCode: String?
        )

        data class OfferResponse(
            @SerializedName("finskyOfferType")
            val finskyOfferType: Int?,
            @SerializedName("listPrice")
            val listPrice: OfferPriceResponse?,
            @SerializedName("retailPrice")
            val retailPrice: OfferPriceResponse?,
            @SerializedName("giftable")
            val giftable: Boolean?
        ) {
            data class OfferPriceResponse(
                @SerializedName("amountInMicros")
                val amountInMicros: Long?,
                @SerializedName("currencyCode")
                val currencyCode: String?
            )
        }
    }

    data class AccessInfoResponse(
        @SerializedName("webReaderLink")
        val webReaderLink: String?,
        @SerializedName("accessViewStatus")
        val accessViewStatus: String?,
    )

    data class SearchInfoResponse(
        @SerializedName("textSnippet")
        val textSnippet: String?
    )
}
