## Why do we need to prevent incidents as much as possible

- Really high cost to resolve (e.g. 10k$ ~100K$ or more)
- May rune the customer service
- Affect company reputation

## Important recorded incidents

- Leaked AWS and other important keys to public repo or pastebin-like sites
- Leaked source code to public repo
- Sent wrong email with important private content
- Sent private email to multiple users
- Sent email from development env to production users
- Using sandbox mode on production (rails c -s or rails console --sandbox)
- Email got hacked (leaked from browser), hacker used accounts to access project private sites
- Update data in wrong environment

## Other incidents
 
- Bad performance on production release
- Removed production database related to ElasticBeanstalk app-db config
- Users’ S3 attachments are not protected (open as public)
- Lacking of basic auth to internal sites (e.g. sidekiq web…)
- SQL injection is not tested properly
- Leaked data because of using free webmail service for testing
- Lacking of basic authentication configuration on the production server before the service release caused any information there crawled by Google
 
 ## Why incidents happened

- Not tested properly or lacking cases (dev and QA)
- Do not have pre-production env (sometimes staging is not enough)
- Copy production data to local without modification to prevent wrong actions by accident (e.g. sending mail from local to production users)
- No unit test for email function
- Lacked of performance and security testing (still happening) 
- Export staging/production data to outside of project’s environment
- Used personal PC for project data without proper security setting 
- Access important data from public internet cafe, access insecure sites, using cracked softwares…

## How to prevent incidents
- Engineer Mindset
- Pull request checklist
- Release checklist
