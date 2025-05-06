package dev.redtronics.mokt.api

import dev.redtronics.mokt.network.client
import dev.redtronics.mokt.network.defaultJson
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

/**
 * Represents the Mojang API client configuration and access details.
 * This is the base interface providing shared properties required
 * for making API requests to Mojang's services.
 *
 * The API relies on an HTTP client for performing requests and a JSON
 * serializer/deserializer for handling request and response transformations.
 *
 * Companion Object:
 * - BASE_URL: Represents the root URL of Mojang's API endpoints.
 *
 * Properties:
 * - httpClient: The HTTP client instance used for performing network operations.
 * - json: The JSON serialization/deserialization instance used for processing API data.
 */
public interface MojangApi {
    public companion object {
        /**
         * Represents the base URL for the Mojang API.
         * All endpoint paths are relative to this URL.
         *
         * Example usage includes operations like retrieving user profiles
         * and performing UUID lookups via HTTP requests.
         */
        public const val BASE_URL: String = "https://api.mojang.com"
    }

    /**
     * Represents the HTTP client instance used to perform network operations
     * for making requests to Mojang's services. This client is responsible
     * for handling HTTP communication, including the request execution and
     * retrieval of responses from the Mojang API.
     */
    public val httpClient: HttpClient
    /**
     * Represents the JSON serialization/deserialization instance used for processing
     * request and response data when interacting with Mojang's API. This instance
     * handles the transformation of Kotlin objects into serialized JSON representations
     * and vice versa, ensuring smooth communication with the API.
     */
    public val json: Json
}

/**
 * A publicly accessible API client for interacting with Mojang's services.
 * This class provides methods to retrieve user-related information such as UUIDs and usernames.
 *
 * This implementation relies on an HTTP client and a JSON serializer for communication
 * and data transformation. By design, it primarily facilitates public access to Mojang's APIs.
 *
 * Constructor parameters:
 * @param httpClient The HTTP client instance used for submitting API requests.
 * @param json The JSON serialization/deserialization instance used for handling data transformations.
 *
 * Inherits:
 * - MojangApi: Provides the base properties for interacting with the Mojang API.
 */
public open class PublicMojangApi internal constructor(
    override val httpClient: HttpClient = client,
    override val json: Json = defaultJson
) : MojangApi {

    /**
     * Retrieves the UUID associated with a given Minecraft username by querying the Mojang API.
     *
     * @param username The Minecraft username for which the UUID is to be retrieved.
     * @return A [Result] containing the [Profile] if the request is successful, or an [Exception]
     * in case of an error.
     */
    public suspend fun getUUID(username: String): Result<Profile> {
        val res = httpClient.get("${MojangApi.BASE_URL}/users/profiles/minecraft/$username")
        if (res.status.value in 200..299) {
            return Result.success(json.decodeFromString(Profile.serializer(), res.body()))
        }

        val err = json.decodeFromString(MojangApiError.serializer(), res.body())
        return Result.failure(err)
    }

    /**
     * Retrieves the Minecraft username associated with a given UUID by querying the Mojang API.
     *
     * @param uuid The UUID of the Minecraft profile for which the username is to be retrieved.
     * @return A [Result] containing the [Profile] if the request is successful, or an [Exception]
     * in case of an error.
     */
    public suspend fun getUsername(uuid: String): Result<Profile> {
        val res = httpClient.get("${MojangApi.BASE_URL}/minecraft/profile/lookup/$uuid")
        if (res.status.isSuccess()) {
            return Result.success(json.decodeFromString(Profile.serializer(), res.body()))
        }

        val err = json.decodeFromString(MojangApiError.serializer(), res.body())
        return Result.failure(err)
    }


    /**
     * Retrieves the UUIDs associated with the given Minecraft usernames by querying the Mojang API.
     *
     * @param usernames The Minecraft usernames for which the UUIDs are to be retrieved.
     * @return A [Result] containing a list of [Profile] objects if the request is successful,
     * or an [Exception] in case of an error.
     */
    public suspend fun getUUID(vararg usernames: String): Result<List<Profile>> {
        val res = httpClient.post("${MojangApi.BASE_URL}/profiles/minecraft") {
            contentType(ContentType.Application.Json)

            // Send a direct array of strings instead of an object with a field containing the array
            setBody(usernames.toList())
        }

        if (res.status.isSuccess()) {
            return Result.success(json.decodeFromString(ListSerializer(Profile.serializer()), res.body()))
        }

        val err = json.decodeFromString(MojangApiError.serializer(), res.body())
        return Result.failure(err)
    }
}

/**
 * A Mojang-API client which grants access to auth required API endpoints.
 *
 * Constructor parameters:
 * @param httpClient The HTTP client instance used for submitting API requests.
 *
 * Inherits:
 * - PublicMojangApi: Extends the public API with additional capabilities or configurations.
 * - MojangApi: Provides base properties required for Mojang API interactions.
 */
public class PrivateMojangApi internal constructor(
    override val httpClient: HttpClient = client,
) : MojangApi, PublicMojangApi() {

}
