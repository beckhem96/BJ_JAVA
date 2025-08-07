안녕하세요\! 알고리즘 마스터입니다. 백준 7579번 '앱' 문제는 코딩 테스트에서 매우 사랑받는 유형인 **냅색(Knapsack) 다이나믹 프로그래밍(DP)** 문제의 아주 좋은 예시입니다.

겉보기엔 복잡해 보이지만, 핵심 아이디어만 파악하면 생각보다 간단하게 해결할 수 있습니다. 저와 함께 한 단계씩 정복해 보시죠\!

### 1\. 문제 분석 (Problem Analysis)

먼저 문제의 요구사항을 명확히 정리해 보겠습니다.

* **입력:**
    * `N`: 활성화된 앱의 개수
    * `M`: 추가로 확보해야 할 최소 메모리 크기
    * `memory[i]`: i번째 앱이 사용 중인 메모리
    * `cost[i]`: i번째 앱을 비활성화하는 비용
* **목표:**
    * 몇 개의 앱을 비활성화해서 **확보된 메모리의 합이 `M` 이상**이 되도록 만들어야 합니다.
    * 이 조건을 만족시키면서, 비활성화한 앱들의 **비용의 합을 최소화**해야 합니다.

예를 들어, 비용 10을 써서 100바이트를 확보할 수도 있고, 비용 12를 써서 120바이트를 확보할 수도 있습니다. 우리는 `M`바이트 이상만 확보하면 되므로, 여러 방법 중 가장 비용이 적게 드는 방법을 찾아야 합니다.

### 2\. 접근 방식 (How to Approach)

이 문제는 전형적인 **0/1 냅색(Knapsack) 문제**와 아주 유사합니다.

* **냅색 문제란?**
    * 가방에 담을 수 있는 최대 무게가 정해져 있을 때, 여러 개의 보석(각각 무게와 가치 존재)을 어떻게 담아야 가치의 총합이 최대가 될까? 하는 문제입니다.

이 문제에 냅색을 적용해 봅시다.

* 각 '앱' = '보석'
* 앱을 비활성화하는 '비용' = 보석의 '무게'
* 앱을 비활성화해서 얻는 '메모리' = 보석의 '가치'

하지만 일반적인 냅색 문제와는 살짝 다릅니다.

* **일반 냅색:** **최대 비용(무게) 한도 내**에서 **최대 메모리(가치) 확보**
* **이 문제:** **최소 메모리(가치) 목표**를 넘기면서 **최소 비용(무게) 찾기**

여기서 결정적인 아이디어가 나옵니다. 만약 DP 테이블을 메모리 기준으로 만들면 `dp[10,000,000]` 과 같이 매우 큰 배열이 필요해서 메모리 초과가 발생합니다. 하지만 \*\*비용의 총합은 최대 `100 * 100 = 10,000`\*\*으로 상대적으로 매우 작습니다.

따라서 우리는 **관점을 바꿔서** DP 테이블을 정의해야 합니다.

> **"비용 `C`를 들여서 확보할 수 있는 최대 메모리는 얼마인가?"**

이 질문에 대한 답을 DP 테이블에 저장하는 것입니다.

### 3\. 핵심 알고리즘 (Knapsack DP)

**1. DP 테이블 정의**

* `dp[c]` : 비용 `c`를 사용해서 확보할 수 있는 **최대 메모리**

**2. DP 점화식**

모든 앱을 하나씩 순회하면서 DP 테이블을 갱신합니다. `i`번째 앱(메모리 `m`, 비용 `c`)을 비활성화할지 말지 결정합니다.

현재 비용 `j`에 대해, `i`번째 앱을 비활성화하는 경우를 생각해 봅시다.

* `i`번째 앱을 비활성화하면 비용 `c`가 들고 메모리 `m`을 얻습니다.
* 따라서, 우리는 이전에 비용 `j - c`를 써서 얻었던 최대 메모리에 `m`을 더할 수 있습니다.
* `dp[j]` 는 `i`번째 앱을 비활성화하지 않았을 때의 값과, 비활성화했을 때의 값을 비교하여 더 큰 값으로 갱신됩니다.

이를 점화식으로 표현하면 다음과 같습니다.

`dp[j] = max(dp[j], dp[j - c] + m)`

**3. DP 테이블 갱신 순서 (매우 중요\!)**

1차원 배열로 냅색 문제를 풀 때, **같은 앱이 여러 번 선택되는 것을 방지**하기 위해 비용(`j`)에 대한 내부 for문은 **큰 값에서 작은 값으로**, 즉 역순으로 순회해야 합니다.

만약 `j`를 작은 값부터 순회하면, `dp[j-c]`를 계산할 때 이미 이번 `i`번째 앱에 대한 정보가 반영된 상태일 수 있습니다. 이는 하나의 앱을 여러 번 사용하는 결과를 낳게 됩니다. 역순으로 순회하면 `dp[j-c]`는 항상 `i`번째 앱이 고려되기 이전의 상태이므로, 0/1 냅색(각 앱을 한 번만 사용)을 올바르게 구현할 수 있습니다.

**4. 정답 찾기**

DP 테이블을 모두 채운 후, `dp` 배열을 `dp[0]`부터 순회하면서 `dp[c]`의 값이 `M` 이상이 되는 \*\*최초의 비용 `c`\*\*가 바로 우리가 찾는 정답입니다.

### 4\. 구현 (Java & Python)

위의 논리를 바탕으로 Java와 Python 코드를 작성해 보겠습니다.

#### **Java 구현**

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] memories = new int[N];
        int[] costs = new int[N];
        int totalCost = 0;

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            memories[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            costs[i] = Integer.parseInt(st.nextToken());
            totalCost += costs[i];
        }

        // dp[c] = 비용 c를 들여 확보할 수 있는 최대 메모리
        int[] dp = new int[totalCost + 1];

        // 모든 앱을 순회
        for (int i = 0; i < N; i++) {
            int memory = memories[i];
            int cost = costs[i];

            // 역순으로 순회 (핵심!)
            for (int j = totalCost; j >= cost; j--) {
                // j 비용으로 i번째 앱을 비활성화하는 경우와 안하는 경우 중 최대 메모리 선택
                // dp[j] : i번째 앱을 비활성화 안 함
                // dp[j - cost] + memory : i번째 앱을 비활성화 함
                dp[j] = Math.max(dp[j], dp[j - cost] + memory);
            }
        }

        // M 바이트 이상을 확보하는 최소 비용 찾기
        int minCost = 0;
        for (int i = 0; i <= totalCost; i++) {
            if (dp[i] >= M) {
                minCost = i;
                break;
            }
        }

        System.out.println(minCost);
    }
}
```

#### **Python 구현**

```python
import sys

# 빠른 입력을 위한 설정
input = sys.stdin.readline

def solve():
    N, M = map(int, input().split())
    memories = list(map(int, input().split()))
    costs = list(map(int, input().split()))
    
    total_cost = sum(costs)
    
    # dp[c] = 비용 c를 들여 확보할 수 있는 최대 메모리
    dp = [0] * (total_cost + 1)
    
    # 모든 앱을 순회
    for i in range(N):
        memory = memories[i]
        cost = costs[i]
        
        # 역순으로 순회 (핵심!)
        for j in range(total_cost, cost - 1, -1):
            # j 비용으로 i번째 앱을 비활성화하는 경우와 안하는 경우 중 최대 메모리 선택
            # dp[j] : i번째 앱을 비활성화 안 함
            # dp[j - cost] + memory : i번째 앱을 비활성화 함
            dp[j] = max(dp[j], dp[j - cost] + memory)

    # M 바이트 이상을 확보하는 최소 비용 찾기
    min_cost = 0
    for i in range(total_cost + 1):
        if dp[i] >= M:
            min_cost = i
            break
            
    print(min_cost)

solve()
```

### 마무리하며

이 문제의 핵심은 \*\*'관점의 전환'\*\*이었습니다. 메모리를 기준으로 DP 테이블을 만들면 너무 커지기 때문에, 상대적으로 작은 **비용을 기준으로 DP 테이블을 정의**하는 것이 해결의 열쇠였습니다.

* **DP 정의:** `dp[비용] = 최대 메모리`
* **핵심 로직:** 0/1 냅색 알고리즘 (1차원 배열 사용 시 역순 순회)

이러한 냅색 DP 유형은 다양한 문제에 응용되니, 이번 기회에 확실히 이해해 두시면 코딩 테스트에서 큰 자신감을 얻으실 수 있을 겁니다.

다른 궁금한 점이나 또 다른 문제가 생기면 언제든지 저를 찾아주세요\! 파이팅입니다\!