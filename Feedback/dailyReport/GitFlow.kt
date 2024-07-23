* Git Flow 에 대해 스터디 하기
* 스터디 한 내용 이 파일에 정리해서 리포트 제출


240723
### PR (Pull Request) ###
새로운 코드 변경사항을 원본 소스 코드에 병합하기 위해 제안하는 것.
1. 깃허브의 내 브런치에서 pullRequest 버튼
2. comparing changes 창
    - base: pull 요청을 받게될 브랜치
    - compare: base로 요청을 보낼 브런치
               즉, auth 브랜치 -> develop 브랜치로 pull 요청을 보낸다는 뜻.
    - Add a title: 제목
    - Add a description: 설명 (왜 pull request를 보내는지 등등)
    - 오른쪽 Reviews나 Labels도 적절히 수정 가능
3. Creat pull Request 버튼

### Git Flow ###
1. Git Flow란
    Git으로 형상관리를 위하여 브랜치와 소스코드를 효율적으로 관리하고 출시하기 위한 브랜치 관리 전략. 프로젝트의 규모나 특성에 관계없이 안정적인 배포와 지속적인 개발을 동시에 관리할 수 있는 방법을 제공.
feature, develop, release, hotfix, master(main)로 나누어 목적별로 활용하는 방법.

2. Git Flow 모델 종류 (총 5가지): master, Hotfix, Release, Develop, Feature
    1) 핵심 브랜치
        - master(main): 운영되는 소스코드가 관리되는 브랜치
        - develop: 개발되고 있는 소스코드가 관리되는 브랜치
    2) support branches (필요에 따라 생성됐다가 제거될 수 있는 브랜치)
        - feature: 기능 개발이 이루어지는 브랜치. 작업이 완료되면 develop에 merge됨.
        - release: develop에서 main으로 merge되기 전에 배포를 준비하는 브랜치. QA(품질 테스트) 수정 등으로 commit들이 쌓일 수 있음. release에서 반영된 내용은 develop과 main으로 반영되며, main으로 반영되면 배포를 수행.
        - hotfix: 운영환경에서 동작하는 main 브랜치에 버그가 발생됐을 때의 버그 수정을 위한 브랜치. 긴급 패치가 필요할 때 사용. hotfix 브랜치는 main과 develop에서 merge 수행.

2-1. 브랜치 이름 규칙
    main
    develop
    feature/{기능명}
    release/{버전}: 버전은 ver0.0.0형태로 표기
    hotfix/{이슈번호 or 버그번호}

3. Git Flow 순서
    1) 브랜치 생성: master(main) - develop - feature 순으로 생성
    2) 브랜치 통합: feature - develop - release - master(main)의 역순서로 진행


4. Merge
    1) fast forward
        git init
        echo "# fast forward" > README.md
        git add -A
        git commit -m "m0"

        # develop 브랜치 생성 - a.txt 파일 생성 - commit 생성
        git branch develop 	# 브랜치 생성
        git switch develop		# 브랜치 전환
        echo 'a' >> a.txt
        git add -A
        git commit -m "add: a.txt"

        # main 브랜치에서 develop 브랜치를 merge
        git switch main
        git merge develop 	# main 브랜치에서 수행
        업데이트 중 2221485..fdaea02
        Fast-forward
         a.txt | 1 +
         1 file changed, 1 insertion(+)
         create mode 100644 a.txt
    # 일반적으로 fast forward는 main 브랜치와 release 브랜치 관계나 main 브랜치와 hotfix 브랜치 관계에서 발생

    2) 3 way merge
        git init
        echo "# 3 way merge" > README.md
        git add -A
        git commit -m "m0"

        # 기능1과 기능2를 만들기 위해 feature/0, feature/1 브랜치 생성.
        git branch feature/0	# feature/0 브랜치 생성
        git branch feature/1	# feature/1 브랜치 생성

        # feature/0 브랜치로 switching, commit 2개 생성
        git switch feature/0
        echo '0-0' > 00.txt	# 00.txt 파일 생성
        # echo "feature/A 브랜치에서 A 기능이 개발 되었습니다" > a.feature
        git add -A
        git commit -m "feature/0-0"
        # git commit -m "A 기능 개발 완료"
        [feature/0 8f66e41] feature/0-0
        # [feature/A f3c2005] A 기능 개발 완료
         1 file changed, 1 insertion(+)
         create mode 100644 00.txt
         # create mode 100644 a.feature
    # 일반적으로는 PR을 날려 merge를 해야하는 측에 merge를 해달라고 요청

        echo '0-1' > 01.txt		# 01.txt 파일 생성
        git add -A
        git commit -m "feature/0-1"
        [feature/0 fd5476d] feature/0-1
         1 file changed, 1 insertion(+)
         create mode 100644 01.txt

        # feature/1 브랜치로 switching: feature/0에서 생성된 내용이 보이지 않는 게 정상. feature/1은 main 브랜치의 m0을 참조한 후 변경사항이 없는 상태.
        git switch feature/1
        echo "1-0" > 10.txt
        git add -A
        git commit -m "feature/1-0”

        # main 브랜치에서 feature/1을 merge. 여기서 fast forward 발생
        git switch main
        git merge feature/1

        업데이트 중 5fbe64f..28a51d3
        Fast-forward
         10.txt | 1 +
         1 file changed, 1 insertion(+)
         create mode 100644 10.txt

        # feature/0 merge
        git merge feature/0

        Merge made by the 'ort' strategy.
         00.txt | 1 +
         01.txt | 1 +
         2 files changed, 2 insertions(+)
         create mode 100644. 00.txt
         create mode 100644. 01.txt

        ## git log는 다음과 같음
        git log --pretty=format: "%h %s" --graph

        *	b0bae3b Merge branch 'feature/0’
        |\
        | *  fd5476d feature/0-1
        | *  fd5476d feature/0-0
        * |  fd5476d feature/1-0
        |/
        *  5fbe64f m0

    # 일반적으로 3 way merge는 develop 브랜치와 feature 브랜치 관계,  develop 브랜치와 release 브랜치 관계, develop 브랜치와 hotfix 브랜치 관계에서 발생할 수 있음.

5. 운영 환경 버그 발생: 버그/이슈는 hotfix에서 처리. 기능 A와 기능 B. 발급된 버그ID는 bug-0으로 가정.
        # main 브랜치에서 hotfix/bug-0 브랜치 생성
        git switch main     # main 브랜치로 스위칭
        git branch hotfix/bug-0     # hotfix 브랜치 생성
        git switch hotfix/bug-0     # hotfix 브랜치로 switching

        # 버그 수정 후
        echo -'# version 1.1\n\nbug-0 해결' > README.md

        git add -A
        git commit -m "bug-0 해결"
        [hotfix/bug-0 1b21f2f] bug-0 해결
         1 file changed, 3 insertions(+), 1 deletion(-)

        # 수정된 버그의 브랜치를 main 브랜치와 develop 브랜치에 merge
        # main 브랜치에서 hotfix 반영
        git switch main
        git merge hotfix/bug-0
        # develop 브랜치에서 hotfix 반영
        git switch develop
        git merge hotfix/bug-0

        Merge made by the 'ort' strategy.
         README.md | 4 +++-
         1 file changed, 3 insertions(+), 1 deletion(-)

        ## develop 브랜치 commit 이력
        git log --pretty=format: "%h %s" --graph

        *	1a674ec Merge branch 'hotfix/bug-0' into develop
        |\
        | *  1b21f2f bug-0 해결
        * |  f3c2005 A 기능 개발 완료
        |/
        *  6d71927 ver1.0.0


6. 기능 B 개발 완료
        git switch feature/B
        echo "feature/B 브랜치에서 B 기능이 개발 되었습니다" > b.feature
        git add -A
        git commit -m "B 기능 개발 완료"
        [feature/B 332d5cc] B 기능 개발 완료
         1 file changed, 1 insertion(+)
         create mode 100644 b.feature

        # develop 브랜치로부터 feature/B 브랜치가 생성되고, develop 브랜치에 변화 발생 -> 3 way merge 발생
        git switch develop
        git merge feature/B

        Merge made by the 'ort' strategy.
         b.feature | 1 +
         1 file changed, 1 insertion(+)
         create mode 100644 b.feature

7. QA를 위한 release 브랜치 생성
        git switch develop
        git branch release/ver2.0.0
        git switch release/ver2.0.0

        # 기획팀에서 B 기능에서 xxx기획이 변경되었는데 반영되지 않은 것 같다는 피드백을 받고 수정
        echo "누락된 기획 추가" >> b.feature

        git add -A
        git commit -m "누락된 기획 추가"

        # QA가 완료되었으므로 release를 반영. 이때 release는 main 브랜치와 함께 develop 브랜치에 반영.

8. 배포
        git switch main
        git merge release/ver2.0.0

        업데이트 중 1b21f2f..c0b129e
        Fast-forward
         a.feature | 1 +
         b.feature | 2 ++
         2 files changed, 3 insertions(+)
         create mode 100644 a.feature
         create mode 100644 b.feature
        # 이때 main 브랜치는 bug-0을 해결하고 추가된 commit이 없으므로 fast forward merge(병합) 수행


        # release 브랜치를 develop 브랜치에 적용. 마찬가지로 fast forward 병합 수행
        git switch develop
        git merge release/ver2.0.0

        업데이트 중 1e79b3b..c0b129e
        Fast-forward
         b.feature | 1 +
         1 file changed, 1 insertion(+)
    # 최종적으로 main 브랜치와 develop 브랜치만 남으며, 나머지 브랜치들은 삭제해도 됨.
    # 사이클 완료