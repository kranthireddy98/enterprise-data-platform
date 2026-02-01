You are a senior full-stack engineer. You are inheriting a **working production e-commerce codebase** with the following already implemented and stable:
- Product catalog
- Cart
- Checkout
- Razorpay Checkout integration for **direct payments**
- Order creation, order status lifecycle, refunds (direct payment refunds only)
- Auth, users, addresses, taxes, shipping, invoices

There is **NO wallet** feature yet.

Your task is to implement a **complete, production-grade Wallet system end-to-end** in ONE PASS with no follow-up questions.  
Assume sensible defaults wherever needed.


GENERAL RULES

- Do NOT ask clarifying questions
- Do NOT produce partial implementations
- Assume Java 17 + Spring Boot backend, REST APIs
- Assume React frontend (or server-rendered UI if already present)
- Assume PostgreSQL (or existing relational DB)
- Follow existing project structure and conventions
- Code must be production-ready, secure, idempotent, and testable
- No hard-coded secrets
- Wallet must integrate cleanly with existing Razorpay flow
- Wallet must NOT break existing direct payment behavior


BUSINESS REQUIREMENTS


WALLET CONCEPT
- Each user has exactly **one wallet**
- Wallet stores balance in **INR**
- Wallet is NOT a payment instrument in Razorpay (internal ledger only)
- Wallet balance can be used:
    - Fully to pay an order
    - Partially with Razorpay covering the remainder

WALLET FUNDING (ADD MONEY)
- User can add money to wallet using Razorpay Checkout
- Flow:
    - Create wallet-topup intent → Razorpay order
    - On successful payment webhook → credit wallet
- Wallet must NOT be credited on frontend success callback
- Must handle retries, duplicate webhooks, and idempotency

WALLET SPENDING
- Wallet can be used during checkout
- User can choose:
    - Use wallet fully
    - Use wallet partially + Razorpay
    - Skip wallet
- Wallet debit happens **only after order is confirmed**
- Wallet debit must be atomic with order creation

REFUNDS
- If order paid via:
    - Wallet only → refund to wallet
    - Wallet + Razorpay → refund wallet portion to wallet, Razorpay portion via Razorpay
    - Razorpay only → existing logic unchanged
- Refund logic must be deterministic and auditable

TRANSACTION LEDGER (CRITICAL)
- Wallet balance must NEVER be updated directly
- Balance must be derived from **ledger entries**
- Ledger entry types:
    - CREDIT_TOPUP
    - DEBIT_ORDER
    - CREDIT_REFUND
    - ADJUSTMENT
- Each entry must store:
    - amount
    - opening balance
    - closing balance
    - reference_id (order_id / razorpay_payment_id)
    - idempotency_key
    - created_at
- Enforce unique idempotency at DB level

CONSISTENCY & SAFETY
- Prevent double spend
- Prevent negative balance
- Handle concurrent checkouts
- Use database locking or optimistic locking where appropriate
- Razorpay webhooks must be verified


TECHNICAL REQUIREMENTS


BACKEND
- Create required entities, migrations, repositories
- WalletService with clear domain logic
- Transactional boundaries clearly defined
- Razorpay webhook handlers updated
- Clean error handling & rollback
- No circular dependencies

API CONTRACTS
- GET /wallet
- POST /wallet/topup/initiate
- POST /wallet/topup/webhook
- POST /checkout (updated to support wallet usage)
- GET /wallet/transactions (paginated)

FRONTEND
- Wallet balance display
- Wallet option in checkout UI
- Wallet top-up screen
- Clear UX for partial payments
- Failure & retry handling

SECURITY
- Auth required on wallet APIs
- Prevent user-to-user wallet access
- Verify Razorpay signatures
- No trust in frontend values

TESTING
- Unit tests for WalletService
- Integration tests for:
    - Wallet debit under concurrency
    - Webhook idempotency
    - Partial payment checkout
- Include edge cases


NON-FUNCTIONAL

- High readability
- Clear comments for critical sections
- No TODOs left behind
- Follow SOLID principles
- Backward compatible with existing orders

DELIVERABLE

Generate all necessary:
- Backend code
- DB migrations
- API changes
- Frontend changes
- Tests

Produce the implementation directly. Do NOT explain concepts. Do NOT summarize. Output only the required changes and code.
