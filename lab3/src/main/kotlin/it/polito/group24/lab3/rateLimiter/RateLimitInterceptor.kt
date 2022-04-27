import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.ConsumptionProbe
import io.github.bucket4j.Refill
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.HandlerInterceptor
import java.time.Duration
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


// Rate limit of 10 requests per minute
class RateLimitInterceptor : HandlerInterceptor {

    var bucket: Bucket = Bucket.builder()
        .addLimit(Bandwidth.classic(
            10,
            Refill.intervally(10, Duration.ofMinutes(1))
        ))
        .build()

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean
    {
        val probe = bucket.tryConsumeAndReturnRemaining(1)
        return if (probe.isConsumed) {
            response.addHeader("X-Rate-Limit-Remaining", probe.remainingTokens.toString())
            true
        } else {
            val waitForRefill = probe.nanosToWaitForRefill / 1000000000
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", waitForRefill.toString())
            response.sendError(
                HttpStatus.TOO_MANY_REQUESTS.value(),
                "You have exhausted your API Request Quota"
            )
            false
        }
    }
}